package com.bridgelabz;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.bridgelabz.EmployeePayrollServiceMain.IOService;

public class EmployeePayrollServiceTest {
	
	@Test
	public void given3Employees_WhenWrittenToFile_ShouldMatchEmployeeEntries() {
		EmployeePayrollDetails[] arrayOfEmployees = {
				new EmployeePayrollDetails(1, "Jeff Bezos", 100000.0, LocalDate.now()),
				new EmployeePayrollDetails(2, "Bill Gates", 200000.0, LocalDate.now()),
				new EmployeePayrollDetails(3, "Mark Zuckerberg", 300000.0, LocalDate.now())};
		
		EmployeePayrollServiceMain employeePayrollService;
		employeePayrollService = new EmployeePayrollServiceMain(Arrays.asList(arrayOfEmployees));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(3, entries);
	}
	
	@Test
	public void givenFile_WhenRead_ShouldReturnNumberOfEntries() {
		EmployeePayrollServiceMain employeePayrollService = new EmployeePayrollServiceMain();
		long entries = employeePayrollService.readDataFromFile(IOService.FILE_IO);
		Assert.assertEquals(3, entries);
	}

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount(){
		EmployeePayrollServiceMain employeePayrollService = new EmployeePayrollServiceMain();
		List<EmployeePayrollDetails> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		System.out.println(employeePayrollData.size());
		Assert.assertEquals(4, employeePayrollData.size());
	}
	
	@Test 
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
		EmployeePayrollServiceMain employeePayrollService = new EmployeePayrollServiceMain();
		List<EmployeePayrollDetails> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("Bill", 7000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bill");
		Assert.assertFalse(result);
	}
	
	@Test
	public void givenName_WhenFound_ShouldReturnEmployeeDetails() {
		EmployeePayrollServiceMain employeePayrollService = new EmployeePayrollServiceMain();
		String name = "Rosa Diaz";
		List<EmployeePayrollDetails> employeePayrollData = employeePayrollService.getEmployeeDetailsBasedOnName(IOService.DB_IO, name);
		String resultName = employeePayrollData.get(0).employeeName;
		Assert.assertEquals(name, resultName);
	}

	@Test
	public void givenStartDateRange_WhenMatches_ShouldReturnEmployeeDetails() {
		String startDate = "2013-01-01";
		EmployeePayrollServiceMain employeePayrollService = new EmployeePayrollServiceMain();
		List<EmployeePayrollDetails> employeePayrollData = employeePayrollService.getEmployeeDetailsBasedOnStartDate(IOService.DB_IO, startDate);
		System.out.println(employeePayrollData.size());
		Assert.assertEquals(1, employeePayrollData.size());
	}
}