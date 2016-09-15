package ru.sbt.bit.ood.solid.homework.db;

public class EmployeeSalary {

    private final String employee;
    private final double salary;

    public EmployeeSalary(String employee, double salary) {
        this.employee = employee;
        this.salary = salary;
    }

    public String getEmployee() {
        return employee;
    }

    public double getSalary() {
        return salary;
    }
}
