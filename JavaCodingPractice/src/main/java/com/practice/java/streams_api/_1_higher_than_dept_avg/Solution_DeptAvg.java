/*
 * ============================================================================
 * Java Streams API Solution: Employees Earning Higher Than Department Average
 * ============================================================================
 *
 * Problem Description:
 * Given a list of Employee objects across different departments (IT, HR, Finance),
 * find and display all employees whose salary is strictly greater than the
 * average salary of their respective department.
 *
 * Intuition:
 * To compare an employee's salary against their department average, we first
 * need to know what the average salary for each department is.
 * 1. Pass 1: Aggregate employee salaries by department using `Collectors.groupingBy`
 *    and compute the average salary using `Collectors.averagingDouble`.
 *    This produces a department lookup map: `Map<String, Double> deptAverages`.
 * 2. Pass 2: Filter the original employee list to retain only those whose salary
 *    strictly exceeds `deptAverages.get(employee.getDeptName())`.
 *
 * Visual Trace:
 * ----------------------------------------------------------------------------
 * Department Averages:
 *   - "IT"      : (75,000 + 90,000 + 60,000) / 3 = 75,000.00
 *   - "HR"      : (55,000 + 65,000 + 50,000) / 3 = 56,666.67
 *   - "Finance" : (80,000 + 70,000 + 95,000) / 3 = 81,666.67
 *
 * Filtering Stage:
 *   - Alice   (IT, 75k)      > 75k      -> FALSE (equal, not strictly greater)
 *   - Bob     (IT, 90k)      > 75k      -> TRUE  -> KEEP
 *   - Charlie (IT, 60k)      > 75k      -> FALSE
 *   - David   (HR, 55k)      > 56.67k   -> FALSE
 *   - Eva     (HR, 65k)      > 56.67k   -> TRUE  -> KEEP
 *   - Frank   (HR, 50k)      > 56.67k   -> FALSE
 *   - Grace   (Finance, 80k) > 81.67k   -> FALSE
 *   - Hank    (Finance, 70k) > 81.67k   -> FALSE
 *   - Ivy     (Finance, 95k) > 81.67k   -> TRUE  -> KEEP
 *
 * Result: [Bob, Eva, Ivy]
 * ----------------------------------------------------------------------------
 *
 * Complexity Analysis:
 * - Time Complexity:  O(N), where N is the number of employees.
 *                     Pass 1 visits each employee once to build department averages (O(N)).
 *                     Pass 2 filters the list in O(N), with O(1) map lookups.
 * - Space Complexity: O(D), where D is the number of distinct departments to store averages.
 * ============================================================================
 */
package com.practice.java.streams_api._1_higher_than_dept_avg;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution_DeptAvg {

    public static void main(String[] args) {
        List<Employee> employees = Challenge_DeptAvg.getSampleEmployees();

        System.out.println("=================================================");
        System.out.println("Input Employees:");
        employees.forEach(System.out::println);
        System.out.println("=================================================");

        // Solution 1: Two-pass stream (Recommended)
        System.out.println("\n--- Solution 1: Two-Pass Stream with GroupingBy (Recommended) ---");
        List<Employee> result1 = solution1_TwoPassGrouping(employees);
        result1.forEach(System.out::println);

        // Solution 2: Single pipeline with collectingAndThen
        System.out.println("\n--- Solution 2: Single Pipeline with collectingAndThen ---");
        List<Employee> result2 = solution2_CollectingAndThen(employees);
        result2.forEach(System.out::println);

        // Solution 3: Grouping by department followed by flatMap
        System.out.println("\n--- Solution 3: Grouping by Department and FlatMap ---");
        List<Employee> result3 = solution3_FlatMap(employees);
        result3.forEach(System.out::println);
    }

    /**
     * Solution 1: Two-Pass Stream with GroupingBy (Recommended — Clear and Idiomatic)
     *
     * How it works:
     * 1. Check for null or empty input.
     * 2. In Pass 1, collect employees into a map of (department -> average salary)
     *    using `Collectors.groupingBy` and `Collectors.averagingDouble`.
     * 3. In Pass 2, filter the employees whose individual salary strictly exceeds
     *    the department average obtained from the lookup map.
     * 4. Collect matching employees into an unmodifiable list.
     *
     * Complexity:
     * - Time Complexity:  O(N), where N is the number of employees.
     * - Space Complexity: O(D), where D is the number of distinct departments.
     *
     * @param employees list of all employees
     * @return employees earning strictly more than their department average
     */
    public static List<Employee> solution1_TwoPassGrouping(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return Collections.emptyList();
        }

        // Pass 1: Compute the average salary per department
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDeptName,
                        Collectors.averagingDouble(e -> e.getSalary().doubleValue())
                ));

        // Pass 2: Filter employees whose salary exceeds department average
        return employees.stream()
                .filter(employee -> {
                    Double deptAvg = avgSalaryByDept.get(employee.getDeptName());
                    return deptAvg != null && employee.getSalary().doubleValue() > deptAvg;
                })
                .toList();
    }

    /**
     * Solution 2: Single-pipeline solution using Collectors.collectingAndThen
     *
     * How it works:
     * 1. Check for null or empty input.
     * 2. Uses `Collectors.collectingAndThen` to encapsulate the department average calculation
     *    and filtering within a single collector expression.
     * 3. The finisher function receives the department averages map and filters the original
     *    employee stream.
     *
     * Complexity:
     * - Time Complexity:  O(N), where N is the number of employees.
     * - Space Complexity: O(D), where D is the number of distinct departments.
     *
     * @param employees list of all employees
     * @return employees earning strictly more than their department average
     */
    public static List<Employee> solution2_CollectingAndThen(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return Collections.emptyList();
        }

        return employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Employee::getDeptName,
                                Collectors.averagingDouble(e -> e.getSalary().doubleValue())
                        ),
                        avgMap -> employees.stream()
                                .filter(e -> {
                                    Double deptAvg = avgMap.get(e.getDeptName());
                                    return deptAvg != null && e.getSalary().doubleValue() > deptAvg;
                                })
                                .toList()
                ));
    }

    /**
     * Solution 3: Grouping by department followed by flatMap
     *
     * How it works:
     * 1. Check for null or empty input.
     * 2. Groups employees into a `Map<String, List<Employee>>` by department.
     * 3. Streams each department's employee list, calculates the local department average,
     *    and filters employees exceeding that average.
     * 4. Uses `.flatMap()` to flatten each filtered department list into a single result list.
     *
     * Complexity:
     * - Time Complexity:  O(N), where N is the number of employees.
     * - Space Complexity: O(N) to store department lists.
     *
     * @param employees list of all employees
     * @return employees earning strictly more than their department average
     */
    public static List<Employee> solution3_FlatMap(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return Collections.emptyList();
        }

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
