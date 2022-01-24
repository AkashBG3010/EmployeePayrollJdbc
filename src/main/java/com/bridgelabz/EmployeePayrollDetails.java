package com.bridgelabz;

import java.time.LocalDate;

public class EmployeePayrollDetails {
	public int employeeId;
	public String employeeName;
	public double employeeSalary;
	public LocalDate startDate;

	public EmployeePayrollDetails(Integer id, String name, Double salary) {
		this.employeeId = id;
		this.employeeName = name;
		this.employeeSalary = salary;
	}
	
	public EmployeePayrollDetails(Integer id, String name, Double salary, LocalDate startDate) {
		this(id,name,salary);
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "EmployeeId: "+employeeId+", EmployeeName: "+employeeName+", EmployeeSalary: "+employeeSalary;
	}
	
	@Override
	public boolean equals(Object object) {
		if(this == object)
			return true;
		if(object == null || getClass() != object.getClass())
			return false;
		EmployeePayrollDetails that = (EmployeePayrollDetails) object;
		return employeeId == that.employeeId && Double.compare(that.employeeSalary,  employeeSalary) == 0 && employeeName.equals(that.employeeName);
	}
}