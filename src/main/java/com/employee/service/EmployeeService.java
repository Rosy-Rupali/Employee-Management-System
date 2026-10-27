
package com.employee.service;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

    private final EmployeeDAO dao = new EmployeeDAO();

    // CREATE
    public void addEmployee(String name, String email,
                            double salary, int departmentId)
            throws SQLException {

        Employee e = new Employee(
                name, email, salary, departmentId
        );

        dao.addEmployee(e);
    }

    // READ: View all employees
    public List<Employee> getAllEmployees() throws SQLException {
        return dao.getAllEmployees();
    }

    // READ: Search by ID
    public Employee findById(int id) throws SQLException {
        return dao.findById(id);
    }

    // UPDATE
    public void updateEmployee(int id, String name, String email,
                               double salary, int departmentId)
            throws SQLException {

        Employee e = new Employee(
                id, name, email, salary, departmentId
        );

        dao.updateEmployee(e);
    }

    // DELETE
    public void deleteEmployee(int id) throws SQLException {
        dao.deleteEmployee(id);
    }

    // READ: Employees by department
    public List<Employee> getEmployeesByDepartment(int departmentId)
            throws SQLException {

        return dao.getEmployeesByDepartment(departmentId);
    }
}
