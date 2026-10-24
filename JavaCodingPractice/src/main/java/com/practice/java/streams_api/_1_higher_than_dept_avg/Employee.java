package com.practice.java.streams_api._1_higher_than_dept_avg;

import java.math.BigDecimal;
import java.util.Objects;

public class Employee {
    private Integer employeeId;
    private String employeeName;
    private String deptName;
    private BigDecimal salary;

    public Employee(Integer employeeId, String employeeName, String deptName, BigDecimal salary) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.deptName = deptName;
        this.salary = salary;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(employeeName, employee.employeeName) &&
                Objects.equals(deptName, employee.deptName) &&
                Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, deptName, salary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + employeeId +
                ", name='" + employeeName + '\'' +
                ", dept='" + deptName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
