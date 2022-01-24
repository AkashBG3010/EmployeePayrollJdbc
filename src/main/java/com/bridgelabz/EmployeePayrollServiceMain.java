package com.bridgelabz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollServiceMain {
	private List<EmployeePayrollDetails> employeePayrollList;
	private EmployeePayrollService employeePayrollDBService;
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}
	
	public EmployeePayrollServiceMain(List<EmployeePayrollDetails> employeePayrollList) {
		this();
		this.employeePayrollList = employeePayrollList;
	}

	public EmployeePayrollServiceMain() {
		employeePayrollDBService =  EmployeePayrollService.getInstance();
	}

	public void printData(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO)) new EmployeePayrollServiceIO().printData();
	}
	
	public long countEntries(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO)) 
			return new EmployeePayrollServiceIO().countEntries();
		return 0;
	}
	
	private void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter the Employee Id : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter the Employee Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter the Employee Salary : ");
		double salary = consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollDetails(id, name, salary));
	}
	
	private EmployeePayrollDetails getEmployeePayrollData(String name) {
		return this.employeePayrollList.stream()
				.filter(EmployeePayrollDataItem -> EmployeePayrollDataItem.employeeName.equals(name))
				.findFirst()
				.orElse(null);
	}
	
	public long readDataFromFile(IOService fileIo) {
		List<String> employeePayrollFromFile = new ArrayList<String>();
		if(fileIo.equals(IOService.FILE_IO)) {
			System.out.println("Employee Details from payroll-file.txt");
			employeePayrollFromFile = new EmployeePayrollServiceIO().readDataFromFile();
		}
		return employeePayrollFromFile.size();
	}
	
	public List<EmployeePayrollDetails> readEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.readData();
		return this.employeePayrollList;
	}
	
	public void writeEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Employee Payroll Roster to Console\n" + employeePayrollList);

		else if(ioService.equals(IOService.FILE_IO))
			new EmployeePayrollServiceIO().writeData(employeePayrollList);
	}	

	public void updateEmployeeSalary(String name, double salary) {
		int result = employeePayrollDBService.updateEmployeeData(name,salary);
		if(result == 0) 
			return;

		EmployeePayrollDetails employeePayrollData = this.getEmployeePayrollData(name);
		if(employeePayrollData != null)
			employeePayrollData.employeeSalary = salary;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		List<EmployeePayrollDetails> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}
	
	public List<EmployeePayrollDetails> getEmployeeDetailsBasedOnStartDate(IOService ioService, String startDate) {
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnStartDateUsingStatement(startDate);
		return this.employeePayrollList;
	}

	public List<EmployeePayrollDetails> getEmployeeDetailsBasedOnStartDateUsingPreparedStatement(IOService ioService, String startDate) {
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnStartDateUsingPreparedStatement(startDate);
		return this.employeePayrollList;
	}
	
	public List<EmployeePayrollDetails> getEmployeeDetailsBasedOnName(IOService ioService, String name) {
		if(ioService.equals(IOService.DB_IO))
			this.employeePayrollList = employeePayrollDBService.getEmployeeDetailsBasedOnNameUsingStatement(name);
		return this.employeePayrollList;
	}

	public static void main(String[] args) {
		System.out.println("---------- Welcome To Employee Payroll Application ----------\n");
		ArrayList<EmployeePayrollDetails> employeePayrollList  = new ArrayList<EmployeePayrollDetails>();
		EmployeePayrollServiceMain employeePayrollService = new EmployeePayrollServiceMain(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayrollService.readEmployeePayrollData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);		
	}
}