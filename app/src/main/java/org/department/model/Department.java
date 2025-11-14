    package org.department.model;

    import java.util.HashSet;
    import java.util.Set;

    import org.employee.model.Employee;

    import jakarta.persistence.CascadeType;
    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.OneToMany;
    import jakarta.persistence.OneToOne;
    import jakarta.persistence.Table;

    @Entity
    @Table(name="Department")
    public class Department {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO )
        private int departmentId;

        @Column(name = "name")
        private String name;

        @OneToMany(mappedBy = "department", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
        Set<Employee> departmentEmployees = new HashSet<>();


        @OneToOne
        @JoinColumn(name = "managerEmployeeId", referencedColumnName = "employeeId", nullable = true)
        private Employee managerEmployee;



        public void addEmployeeToDepartment(Employee emp){
            departmentEmployees.add(emp);

        }
        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<Employee> getDepartmentEmployees() {
            return departmentEmployees;
        }

        public void setDepartmentEmployees(Set<Employee> departmentEmployees) {
            this.departmentEmployees = departmentEmployees;
        }

        public Employee getManagerEmployee() {
            return managerEmployee;
        }

        public void setManagerEmployee(Employee managerEmployee) {
            this.managerEmployee = managerEmployee;
        }
        public void removeEmployeeFromSet(Employee emp) {
            
            this.departmentEmployees.remove(emp);
        }



    

    }
