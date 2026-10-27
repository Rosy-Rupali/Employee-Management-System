package com.employee.main;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.employee.util.DatabaseUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeMgmtApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final EmployeeService service = new EmployeeService();

    public static void main(String[] args) {
        try {
            System.out.println("Employee Management System Started");

            boolean running = true;

            while (running) {
                System.out.println("\n===== EMPLOYEE MANAGEMENT =====");
                System.out.println("1. Add employee");
                System.out.println("2. View all employees");
                System.out.println("3. Search employee by ID");
                System.out.println("4. Update employee");
                System.out.println("5. Delete employee");
                System.out.println("6. Display employees by department");
                System.out.println("0. Exit");

                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(sc.nextLine());

                try {
                    switch (choice) {
                        case 1:
                            addEmployee();
                            break;

                        case 2:
                            showAllEmployees();
                            break;

                        case 3:
                            searchEmployee();
                            break;

                        case 4:
                            updateEmployee();
                            break;

                        case 5:
                            deleteEmployee();
                            break;

                        case 6:
                            showDepartmentEmployees();
                            break;

                        case 0:
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Application error: " + e.getMessage());
        } finally {
            sc.close();
        }

        System.out.println("Application closed.Thank You!!");
    }

    // CREATE
    private static void addEmployee() throws SQLException {
        System.out.print("Employee name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Salary: ");
        double salary = Double.parseDouble(sc.nextLine());

        System.out.print("Department ID: ");
        int departmentId = Integer.parseInt(sc.nextLine());

        service.addEmployee(name, email, salary, departmentId);

        System.out.println("Added employee successfully.");
    }

    // READ: View all employees
    private static void showAllEmployees() throws SQLException {
        List<Employee> employees = service.getAllEmployees();

        if (employees.isEmpty()) {
            System.err.println("No records found.");
            return;
        }

        employees.forEach(System.out::println);
    }

    // READ: Search employee by ID
    private static void searchEmployee() throws SQLException {
        System.out.print("Enter employee ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Employee e = service.findById(id);

        if (e == null) {
            System.err.println("Employee not found.");
        } else {
            System.out.println(e);
        }
    }

    // UPDATE
    private static void updateEmployee() throws SQLException {
        System.out.print("Enter employee ID to update: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("New name: ");
        String name = sc.nextLine();

        System.out.print("New email: ");
        String email = sc.nextLine();

        System.out.print("New salary: ");
        double salary = Double.parseDouble(sc.nextLine());

        System.out.print("New department ID: ");
        int departmentId = Integer.parseInt(sc.nextLine());

        service.updateEmployee(
                id, name, email, salary, departmentId
        );

        System.out.println("Updated employee record successfully.");
    }

    // DELETE
    private static void deleteEmployee() throws SQLException {
        System.out.print("Enter employee ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        service.deleteEmployee(id);

        System.out.println("Deleted employee record successfully.");
    }

    // READ: Employees by department
    private static void showDepartmentEmployees() throws SQLException {
        System.out.print("Enter department ID: ");
        int departmentId = Integer.parseInt(sc.nextLine());

        List<Employee> employees =
                service.getEmployeesByDepartment(departmentId);

        if (employees.isEmpty()) {
            System.err.println("No records found.");
            return;
        }

        employees.forEach(System.out::println);
    }

}
