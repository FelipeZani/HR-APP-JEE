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

    public List<Employee> getEmployeeByParameters(String param1, String param2, String param3, String param4) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            StringBuilder hib = new StringBuilder("from Employee e where 1=1");
            int countNullVariables = 0;


            if (param1 != null && !param1.equals("")) {
                hib.append(" and e.id = :param1");
                countNullVariables++;
            }

            if (param2 != null && !param2.equals("")) {
                hib.append(" and e.name = :param2");
                countNullVariables++;
            }

            if (param3 != null && !param3.equals("")) {
                hib.append(" and e.lastname = :param3");
                countNullVariables++;
            }

            if (param4 != null && !param4.equals("")) {
                hib.append(" and e.department = :param4");
                countNullVariables++;
            }
            if(countNullVariables == 0){
                throw new IllegalArgumentException("At least one paremeter is required to research an employee");
            }

            Query<Employee> query = session.createQuery(hib.toString(), Employee.class);

            if (param1 != null && !param1.equals(""))
                query.setParameter("param1", param1);

            if (param2 != null && !param2.equals(""))
                query.setParameter("param2", param2);

            if (param3 != null && !param3.equals(""))
                query.setParameter("param3", param3);

            if (param4 != null &&  !param4.equals(""))
                query.setParameter("param4", param4);

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
