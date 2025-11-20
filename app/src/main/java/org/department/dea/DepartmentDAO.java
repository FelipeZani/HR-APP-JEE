package org.department.dea;

import java.util.List;

import org.department.model.Department;

import org.hibernate.query.Query;
import org.util.HibernateUtil;

import org.employee.model.Employee;
import org.hibernate.Session;

public class DepartmentDAO {

    public void addDepartment(Department d) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(d);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateDepartment(Department department) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();
            session.merge(department);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDepartmentWithManager(int departmentId, String newName, int managerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Department department = session.get(Department.class, departmentId);
            if (department == null) {
                session.getTransaction().commit();
                return;
            }

            // Update department name
            department.setName(newName);

            // First, remove this employee from any other department where they are manager
            if (managerId > 0) {
                Query<Department> query = session.createQuery("FROM Department d WHERE d.managerEmployee.employeeId = :empId", Department.class);
                query.setParameter("empId", managerId);
                List<Department> departmentsWithThisManager = query.getResultList();
                
                for (Department dept : departmentsWithThisManager) {
                    if (dept.getDepartmentId() != departmentId) {  // Don't remove from the target department
                        dept.setManagerEmployee(null);
                        session.merge(dept);
                    }
                }

                Employee employee = session.get(Employee.class, managerId);
                if (employee != null) {
                    department.setManagerEmployee(employee);
                }
            } else {
                // If managerId is 0 or negative, set manager to null
                department.setManagerEmployee(null);
            }

            session.merge(department);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Department getDepartementByName(String name) throws Exception {

        if (name == null || name.isEmpty()) {
            throw new Exception("Department name is null or empty");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            String hib = "from Department d where d.name = :name";

            Query<Department> query = session.createQuery(hib, Department.class);
            query.setParameter("name", name);

            Department department = query.uniqueResult();

            return department;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Department> getDepartementList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Department> query = session.createQuery("FROM Department", Department.class);
            List<Department> d = query.getResultList();
            session.getTransaction().commit();
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void managerAffectation(int employeeId, int departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Department department = session.get(Department.class, departementId);
            if (department == null) {
                session.getTransaction().commit();
                return;
            }

            // First, remove this employee from any other department where they are manager
            Query<Department> query = session.createQuery("FROM Department d WHERE d.managerEmployee.employeeId = :empId", Department.class);
            query.setParameter("empId", employeeId);
            List<Department> departmentsWithThisManager = query.getResultList();
            
            for (Department dept : departmentsWithThisManager) {
                if (dept.getDepartmentId() != departementId) {  // Don't remove from the target department
                    dept.setManagerEmployee(null);
                    session.merge(dept);
                }
            }

            Employee employee = session.get(Employee.class, employeeId);
            // allow setting manager to null if employee not found
            department.setManagerEmployee(employee);

            session.merge(department);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDepartment(int departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Department department = session.get(Department.class, departementId);
            if (department == null) {
                session.getTransaction().commit();
                return;
            }

            // Detach or nullify department reference from employees to avoid FK constraint issues
            if (department.getDepartmentEmployees() != null) {
                for (Employee e : department.getDepartmentEmployees()) {
                    e.setDepartment(null);
                    session.merge(e);
                }
            }

            // Now remove the department
            session.remove(department);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}