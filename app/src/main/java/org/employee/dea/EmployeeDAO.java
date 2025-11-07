package org.employee.dea;

import java.util.List;

import org.employee.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.util.HibernateUtil;

public class EmployeeDAO {

    public void addEmployee(Employee emp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(emp);
        session.getTransaction().commit();

        session.close();
    }

    public List<Employee> getAllEmployees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Employee> employeesList = session.createQuery("from Employee", Employee.class)
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

    public List<Employee> getEmployeeByParameters(String id, String name, String lastname, String department) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {


            

            StringBuilder hib = new StringBuilder("from Employee e where 1=1");
            int countNullVariables = 0;


            if (id != null && !id.equals("")) {
                hib.append(" and e.id = :id");
                countNullVariables++;
            }

            if (name != null && !name.equals("")) {
                hib.append(" and e.name = :name");
                countNullVariables++;
            }

            if (lastname != null && !lastname.equals("")) {
                hib.append(" and e.lastname = :lastname");
                countNullVariables++;
            }

            if (department != null && !department.equals("")) {
                hib.append(" and e.department = :department");
                countNullVariables++;
            }
            if(countNullVariables == 0){
                throw new IllegalArgumentException("At least one paremeter is required to research an employee");
            }

            Query<Employee> query = session.createQuery(hib.toString(), Employee.class);

            if (id != null && !id.equals(""))
                query.setParameter("id", id);

            if (name != null && !name.equals(""))
                query.setParameter("name", name);

            if (lastname != null && !lastname.equals(""))
                query.setParameter("lastname", lastname);

            if (department != null &&  !department.equals(""))
                query.setParameter("department", department);

            List<Employee> empList = query
                    .setMaxResults(20)
                    .list();

            return empList;

        }catch(IllegalArgumentException e){
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

}
