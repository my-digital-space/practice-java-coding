/*
 * ============================================================================
 * Java Streams API Solution: Employees Earning Higher Than Department Average
 * ============================================================================
 *
 * Problem Description:
 * Given a list of Employee objects across different departments (IT, HR, Finance),
 * use the Java Streams API to find and display all employees whose salary is
 * strictly greater than the average salary of their respective department.
 *
 * Steps to solve:
 * 1. Compute the average salary for each department using groupingBy and averaging collectors.
 * 2. Filter employees whose individual salary exceeds their department's average.
 * 3. Collect or print the matching employees.
 *
 * Solution Approach:
 * 1. Calculate Department Averages:
 *    - Use Collectors.groupingBy with Employee::getDeptName as the classifier.
 *    - Use Collectors.averagingDouble(e -> e.getSalary().doubleValue()) as downstream collector.
 *    - Produces a Map<String, Double> of department name -> average salary.
 *
 * 2. Filter Employees Exceeding Department Average:
 *    - Stream the original employee list.
 *    - Filter each employee where employee.getSalary().doubleValue() > avgSalaryByDept.get(deptName).
 *    - Collect results into a List<Employee> using .toList().
 * ============================================================================
 */
package com.practice.java.streams_api._1_higher_than_dept_avg;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution_DeptAvg {

    public static void main(String[] args) {
        List<Employee> employees = Challenge_DeptAvg.getSampleEmployees();

        System.out.println("--- All Employees ---");
        employees.forEach(System.out::println);

        // 1. Calculate department average salaries
        Map<String, Double> avgSalaryByDept =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDeptName,
                                Collectors.averagingDouble(e -> e.getSalary().doubleValue())
                        ));

        System.out.println("\n--- Department Average Salaries ---");
        avgSalaryByDept.forEach((dept, avg) ->
                System.out.printf("Department: %-10s | Average Salary: %.2f%n", dept, avg));

        // 2. Find employees earning more than their department's average
        List<Employee> employeesAboveDeptAvg =
            employees.stream()
                .filter(employee -> employee.getSalary().doubleValue() >
                            avgSalaryByDept.get(employee.getDeptName()))
                .toList();

        System.out.println("\n--- Employees Earning More Than Department Average ---");
        employeesAboveDeptAvg.forEach(System.out::println);
    }

    /**
     * Alternative Approach (Option 1): Single-statement solution using Collectors.collectingAndThen.
     * Computes department averages into an intermediate Map and immediately filters the employee stream
     * in the collector's finisher function.
     * Time Complexity: O(N)
     *
     * @param employees list of all employees
     * @return employees earning more than their department average
     */
    public static List<Employee> findEmployeesAboveDeptAvgUsingCollectingAndThen(
            List<Employee> employees) {
        return employees.stream()
            .collect(Collectors.collectingAndThen(
                    Collectors.groupingBy(
                            Employee::getDeptName,
                            Collectors.averagingDouble(e -> e.getSalary().doubleValue())
                    ),
                    avgMap -> employees.stream()
                            .filter(e -> e.getSalary().doubleValue() >
                                                    avgMap.get(e.getDeptName()))
                            .toList()
            ));
    }

    /**
     * Alternative Approach (Option 2): Grouping by department followed by flatMap.
     * Groups employees by department, computes the average for each department group,
     * and filters each department's employee list before flattening the result.
     * Time Complexity: O(N)
     *
     * @param employees list of all employees
     * @return employees earning more than their department average
     */
    public static List<Employee> findEmployeesAboveDeptAvgUsingFlatMap(
            List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName))
                .values()
                .stream()
                .flatMap(deptList -> {
                    double avg = deptList.stream()
                            .mapToDouble(e -> e.getSalary().doubleValue())
                            .average()
                            .orElse(0.0);
                    return deptList.stream().filter(e -> e.getSalary().doubleValue() > avg);
                })
                .toList();
    }
}
