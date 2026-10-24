package com.practice.java.streams_api._1_higher_than_dept_avg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Challenge: Employees Higher Than Department Average Salary Tests")
class ChallengeTest {

    @Nested
    @DisplayName("Sample Data Scenarios")
    class SampleDataTests {

        @Test
        @DisplayName("Should return employees whose salary is strictly greater than their department average")
        void testSampleDataEmployeesHigherThanDeptAvg() {
            List<Employee> employees = Challenge.getSampleEmployees();

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            assertEquals(3, result.size(), "Expected exactly 3 employees earning above department average");

            List<Integer> expectedIds = List.of(102, 202, 303); // Bob (IT), Eva (HR), Ivy (Finance)
            List<Integer> actualIds = result.stream().map(Employee::getEmployeeId).toList();

            assertTrue(actualIds.containsAll(expectedIds),
                    "Result should contain Bob (102), Eva (202), and Ivy (303)");
        }

        @Test
        @DisplayName("Should not include employees whose salary is exactly equal to the department average")
        void testEmployeeWithSalaryEqualToDepartmentAverageIsNotIncluded() {
            List<Employee> employees = Challenge.getSampleEmployees();

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            // Alice in IT earns 75,000 which is exactly the average of IT (75,000 + 90,000 + 60,000) / 3 = 75,000
            boolean containsAlice = result.stream().anyMatch(e -> e.getEmployeeId() == 101);
            assertFalse(containsAlice, "Alice earns exactly the department average and should NOT be included");
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should return an empty list when given an empty employee list")
        void testEmptyEmployeeListReturnsEmptyList() {
            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(Collections.emptyList());

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty for an empty employee list");
        }

        @Test
        @DisplayName("Should handle null input gracefully by returning an empty list")
        void testNullInputReturnsEmptyList() {
            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(null);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(), "Result should be empty when input is null");
        }

        @Test
        @DisplayName("Should return empty list when a department has only one employee (salary == average)")
        void testSingleEmployeeInDepartmentReturnsEmptyList() {
            List<Employee> employees = List.of(
                    new Employee(1, "Solo IT", "IT", new BigDecimal("80000")),
                    new Employee(2, "Solo HR", "HR", new BigDecimal("60000"))
            );

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(),
                    "When a department has only 1 employee, salary equals the average, so none should be returned");
        }

        @Test
        @DisplayName("Should return empty list when all employees in a department have identical salaries")
        void testAllEmployeesInDepartmentWithIdenticalSalariesReturnsEmptyList() {
            List<Employee> employees = List.of(
                    new Employee(1, "Emp1", "IT", new BigDecimal("60000")),
                    new Employee(2, "Emp2", "IT", new BigDecimal("60000")),
                    new Employee(3, "Emp3", "IT", new BigDecimal("60000"))
            );

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            assertTrue(result.isEmpty(),
                    "When all employees earn the exact same salary, none earn strictly more than the average");
        }
    }

    @Nested
    @DisplayName("Advanced & Departmental Scenarios")
    class AdvancedScenariosTests {

        @Test
        @DisplayName("Should return multiple employees when more than one employee exceeds the department average")
        void testMultipleEmployeesInSameDepartmentExceedingAverage() {
            // IT Department: 10,000 + 20,000 + 100,000 + 110,000 = 240,000 / 4 = 60,000
            // Employees 3 and 4 earn above 60,000
            List<Employee> employees = List.of(
                    new Employee(1, "Junior 1", "IT", new BigDecimal("10000")),
                    new Employee(2, "Junior 2", "IT", new BigDecimal("20000")),
                    new Employee(3, "Senior 1", "IT", new BigDecimal("100000")),
                    new Employee(4, "Senior 2", "IT", new BigDecimal("110000"))
            );

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            assertEquals(2, result.size());

            List<Integer> resultIds = result.stream().map(Employee::getEmployeeId).toList();
            assertTrue(resultIds.containsAll(List.of(3, 4)), "Expected Senior 1 (3) and Senior 2 (4)");
        }

        @Test
        @DisplayName("Should correctly handle mixed departments where some have earners above average and some do not")
        void testMixedDepartments() {
            // HR has identical salaries (none above avg)
            // Sales has one above average: 40k + 80k = 120k / 2 = 60k -> Emp 4 (80k) is above avg
            List<Employee> employees = List.of(
                    new Employee(1, "HR1", "HR", new BigDecimal("50000")),
                    new Employee(2, "HR2", "HR", new BigDecimal("50000")),
                    new Employee(3, "Sales1", "Sales", new BigDecimal("40000")),
                    new Employee(4, "Sales2", "Sales", new BigDecimal("80000"))
            );

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            assertEquals(1, result.size());
            assertEquals(4, result.get(0).getEmployeeId());
        }

        @Test
        @DisplayName("Should handle precise decimal salaries accurately")
        void testDecimalSalariesPrecision() {
            // Finance: 50000.25 + 50000.75 + 50000.50 = 150001.50 / 3 = 50000.50
            // Only Emp 2 (50000.75) is strictly greater than 50000.50
            List<Employee> employees = List.of(
                    new Employee(1, "Fin1", "Finance", new BigDecimal("50000.25")),
                    new Employee(2, "Fin2", "Finance", new BigDecimal("50000.75")),
                    new Employee(3, "Fin3", "Finance", new BigDecimal("50000.50"))
            );

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            assertEquals(1, result.size());
            assertEquals(2, result.get(0).getEmployeeId());
        }

        @Test
        @DisplayName("Should correctly identify single high outlier in a large department")
        void testSingleOutlierInDepartment() {
            // Support: 9 employees at 30,000, 1 Lead at 90,000
            // Total = (9 * 30,000) + 90,000 = 360,000 / 10 = 36,000
            // Only Lead (10) earns above 36,000
            List<Employee> employees = Arrays.asList(
                    new Employee(1, "Agent 1", "Support", new BigDecimal("30000")),
                    new Employee(2, "Agent 2", "Support", new BigDecimal("30000")),
                    new Employee(3, "Agent 3", "Support", new BigDecimal("30000")),
                    new Employee(4, "Agent 4", "Support", new BigDecimal("30000")),
                    new Employee(5, "Agent 5", "Support", new BigDecimal("30000")),
                    new Employee(6, "Agent 6", "Support", new BigDecimal("30000")),
                    new Employee(7, "Agent 7", "Support", new BigDecimal("30000")),
                    new Employee(8, "Agent 8", "Support", new BigDecimal("30000")),
                    new Employee(9, "Agent 9", "Support", new BigDecimal("30000")),
                    new Employee(10, "Lead", "Support", new BigDecimal("90000"))
            );

            List<Employee> result = Challenge.findEmployeesHigherThanDeptAvg(employees);

            assertNotNull(result, "Result should not be null");
            assertEquals(1, result.size());
            assertEquals(10, result.get(0).getEmployeeId());
        }
    }
}
