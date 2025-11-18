package org.employee.dea;

import java.util.List;

import org.department.model.Department;
import org.employee.model.Account;
import org.employee.model.Employee;
import org.employee.model.Post;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.project.model.Project;
import org.util.HibernateUtil;

public class EmployeeDAO {

    public void addEmployee(Employee emp) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.persist(emp);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateEmployee(Employee emp) {

        Session session =  HibernateUtil.getSessionFactory().openSession();;
        Transaction trx = null;
        try {
            trx = session.beginTransaction();
            
            session.merge(emp);
            trx.commit();
        } catch (Exception e) {
            if(trx!= null){
                trx.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    

    public List<Employee> getAllEmployees() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Employee> employeesList = session.createQuery("from Employee order by post desc, rank desc", Employee.class)
                    .list();

            if (employeesList.size() <= 0) {
                throw new Exception("Empty employee list at getAllEmployees() function.");
            }

            return employeesList;

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

    }

    public void removeEmployee(String employeeid) {

        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            transaction = session.beginTransaction();

            Employee emp = session.get(Employee.class, employeeid);

            Department derp = emp.getDepartment();
            Account acc = emp.getUserAccount();
            Project projectManagered = emp.getProjectManagered();
            Post post = emp.getPost(); // post and department are parents of the employee clas therefore I delete
                                       // employee records of it
            // Nevertheless I still need them to be present in the database: A Department
            // can exist yet without an employee member/manager
            // and a post without an employee

            post.removeEmployeeFromSet(emp);
            emp.setPost(null);
            session.merge(post);

            derp.removeEmployeeFromSet(emp);

            emp.setDepartment(null);

            if (derp.getManagerEmployee() != null) {
                if (derp.getManagerEmployee().getEmployeeId() == emp.getEmployeeId()) {
                    derp.setManagerEmployee(null);
                }
            }

            session.merge(derp);

            if (projectManagered != null) {
                projectManagered.setProjectManagerEmployee(null);
                session.merge(projectManagered);
            }

            acc.setEmployee(null);

            session.remove(acc);

            emp.removeEmployeeProject();

            emp.setUserAccount(null);

            session.remove(emp);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    public Employee getEmployeeById(String employeeid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            if (employeeid == null || employeeid.isEmpty()) {
                throw new IllegalArgumentException("Id is not valid");
            }

            Employee emp = (Employee) session.get(Employee.class,employeeid);

            return emp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Employee> getEmployeeByParameters(String id, String name, String lastname, String department) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            StringBuilder hib = new StringBuilder("from Employee e where 1=1"); // this will return all employees if no
                                                                                // parameter is valid

            if (id != null && !id.equals("")) {
                hib.append(" and e.id = :id");
            }

            if (name != null && !name.equals("")) {
                hib.append(" and e.name = :name");
            }

            if (lastname != null && !lastname.equals("")) {
                hib.append(" and e.lastname = :lastname");
            }

            if (department != null && !department.equals("")) {
                hib.append(" and e.department = :department");
            }

            Query<Employee> query = session.createQuery(hib.toString(), Employee.class);

            if (id != null && !id.equals(""))
                query.setParameter("id", id);

            if (name != null && !name.equals(""))
                query.setParameter("name", name);

            if (lastname != null && !lastname.equals(""))
                query.setParameter("lastname", lastname);

            if (department != null && !department.equals(""))
                query.setParameter("department", department);

            List<Employee> empList = query
                    .setMaxResults(20)
                    .list();

            return empList;

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean isEmployeeTabEmpty() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Employee> employees = session.createQuery("from Employee", Employee.class)
                    .setMaxResults(1)
                    .list();
            return employees.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account getUserAccount(String username, String hachedPwd) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<Account> queryAcc = session.createQuery("from Account where username= :username and password=:pwd",Account.class);

            queryAcc.setParameter("username", username);
            queryAcc.setParameter("pwd", hachedPwd);

            Account selected = queryAcc.uniqueResult();

            return selected;



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
        

}
