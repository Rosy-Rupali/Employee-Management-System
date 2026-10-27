
package com.employee.dao;

import com.employee.model.Employee;
import com.employee.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // CREATE: Add a new employee
    public void addEmployee(Employee e) throws SQLException {
        String sql = "INSERT INTO employee "
                + "(employee_name, email, salary, department_id) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getEmployeeName());
            ps.setString(2, e.getEmail());
            ps.setDouble(3, e.getSalary());
            ps.setInt(4, e.getDepartmentId());

            ps.executeUpdate();
        }
    }

    // READ: View all employees with department names
    public List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT e.employee_id, e.employee_name, "
                + "e.email, e.salary, e.department_id, "
                + "d.department_name "
                + "FROM employee e "
                + "JOIN department d "
                + "ON e.department_id = d.department_id "
                + "ORDER BY e.employee_id";

        List<Employee> employees = new ArrayList<>();

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        }

        return employees;
    }

    // READ: Search by employee ID
    public Employee findById(int id) throws SQLException {
        String sql = "SELECT e.employee_id, e.employee_name, "
                + "e.email, e.salary, e.department_id, "
                + "d.department_name "
                + "FROM employee e "
                + "JOIN department d "
                + "ON e.department_id = d.department_id "
                + "WHERE e.employee_id = ?";

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
        }

        return null;
    }

    // UPDATE: Change employee details
    public void updateEmployee(Employee e) throws SQLException {
        String sql = "UPDATE employee SET employee_name = ?, "
                + "email = ?, salary = ?, department_id = ? "
                + "WHERE employee_id = ?";

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getEmployeeName());
            ps.setString(2, e.getEmail());
            ps.setDouble(3, e.getSalary());
            ps.setInt(4, e.getDepartmentId());
            ps.setInt(5, e.getEmployeeId());

            ps.executeUpdate();
        }
    }

    // DELETE: Remove an employee
    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id = ?";

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // JOIN: Display employees belonging to one department
    public List<Employee> getEmployeesByDepartment(int departmentId)
            throws SQLException {

        String sql = "SELECT e.employee_id, e.employee_name, "
                + "e.email, e.salary, e.department_id, "
                + "d.department_name "
                + "FROM employee e "
                + "JOIN department d "
                + "ON e.department_id = d.department_id "
                + "WHERE d.department_id = ? "
                + "ORDER BY e.employee_name";

        List<Employee> employees = new ArrayList<>();

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapEmployee(rs));
                }
            }
        }

        return employees;
    }

    // Convert one ResultSet row into an Employee object
    private Employee mapEmployee(ResultSet rs) throws SQLException {
        Employee e = new Employee(
                rs.getInt("employee_id"),
                rs.getString("employee_name"),
                rs.getString("email"),
                rs.getDouble("salary"),
                rs.getInt("department_id")
        );

        e.setDepartmentName(rs.getString("department_name"));
        return e;
    }
}
