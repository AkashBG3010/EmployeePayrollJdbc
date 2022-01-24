package com.bridgelabz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollServiceMain {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	public EmployeePayrollServiceMain() {
	}

	private List<EmployeePayrollDetails> employeePayrollList;

	private void readEmployeePayrollData(Scanner consoleInputReader) {

		System.out.println("Enter the Employee Id : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter the Employee Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter the Employee Salary : ");
		double salary = consoleInputReader.nextDouble();

		employeePayrollList.add(new EmployeePayrollDetails(id, name, salary, LocalDate.now()));
	}

	public EmployeePayrollServiceMain(List<EmployeePayrollDetails> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}

	public void writeEmployeePayrollData(IOService ioService) {
		if(ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Employee Payroll Roster to Console\n" + employeePayrollList);

		else if(ioService.equals(IOService.FILE_IO))
			new EmployeePayrollServiceIO().writeData(employeePayrollList);
	}

	public void printData(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO)) new EmployeePayrollServiceIO().printData();
	}


	public long countEntries(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO)) 
			return new EmployeePayrollServiceIO().countEntries();

		return 0;
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
			this.employeePayrollList = new EmployeePayrollService().readData();
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