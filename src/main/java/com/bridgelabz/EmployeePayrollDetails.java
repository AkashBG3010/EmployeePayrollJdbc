package com.bridgelabz;

import java.time.LocalDate;

public class EmployeePayrollDetails {
	public int employeeId;
	public String employeeName;
	public double employeeSalary;
	public LocalDate startDate;

	public EmployeePayrollDetails(Integer id, String name, Double salary, LocalDate date) {

		this.employeeId = id;
		this.employeeName = name;
		this.employeeSalary = salary;
		this.startDate = date;
	}

	@Override
	public String toString() {

		return "EmployeeId: "+employeeId+", EmployeeName: "+employeeName+", EmployeeSalary: "+employeeSalary;
	}
}