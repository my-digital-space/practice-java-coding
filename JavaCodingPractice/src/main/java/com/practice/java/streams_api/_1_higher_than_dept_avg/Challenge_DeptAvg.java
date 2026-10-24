/*
 * ============================================================================
 * Java Streams API Challenge: Employees Earning Higher Than Department Average
 * ============================================================================
 *
 * Problem Description:
 * You are given a list of Employee objects across different departments.
 * For this challenge, we consider 3 departments:
 *   1. "IT"
 *   2. "HR"
 *   3. "Finance"
 *
 * Objective:
 * Using the Java Streams API, find and display/print all employees whose salary
 * is strictly greater than the average salary of their respective department.
 *
 * Note:
 * Implement your solution inside the findEmployeesHigherThanDeptAvg method below.
 * You can verify your implementation by running the unit tests in Challenge_DeptAvgTest.java.
 * ============================================================================
 */
package com.practice.java.streams_api._1_higher_than_dept_avg;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Challenge_DeptAvg {

    public static void main(String[] args) {
        List<Employee> employees = getSampleEmployees();

        System.out.println("--- All Employees ---");
        employees.forEach(System.out::println);

        System.out.println("\n--- Employees Earning More Than Department Average ---");
        List<Employee> result = findEmployeesHigherThanDeptAvg(employees);
        result.forEach(System.out::println);
    }

    /**
     * Finds all employees whose salary is strictly greater than their department's average salary.
     *
     * @param employees list of employees
     * @return list of employees earning more than their department's average salary
     */
    public static List<Employee> findEmployeesHigherThanDeptAvg(List<Employee> employees) {
        // TODO: Implement this method using Java Streams API
        return Collections.emptyList();
    }

    /**
     * Provides sample employee data with 3 departments: IT, HR, and Finance.
     */
    public static List<Employee> getSampleEmployees() {
        return Arrays.asList(
                // IT Department (Average: 75,000)
                new Employee(101, "Alice", "IT", new BigDecimal("75000")),
                new Employee(102, "Bob", "IT", new BigDecimal("90000")),
                new Employee(103, "Charlie", "IT", new BigDecimal("60000")),

                // HR Department (Average: 56,666.67)
                new Employee(201, "David", "HR", new BigDecimal("55000")),
                new Employee(202, "Eva", "HR", new BigDecimal("65000")),
                new Employee(203, "Frank", "HR", new BigDecimal("50000")),

                // Finance Department (Average: 81,666.67)
                new Employee(301, "Grace", "Finance", new BigDecimal("80000")),
                new Employee(302, "Hank", "Finance", new BigDecimal("70000")),
                new Employee(303, "Ivy", "Finance", new BigDecimal("95000"))
        );
    }
}
