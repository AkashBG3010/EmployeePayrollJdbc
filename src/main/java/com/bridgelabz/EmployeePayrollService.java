package com.bridgelabz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {
	private PreparedStatement employeePayrollDataStatement;
	private static EmployeePayrollService employeePayrollService;

	public EmployeePayrollService() {

	}

	public static EmployeePayrollService getInstance() {
		if(employeePayrollService == null)
			employeePayrollService = new EmployeePayrollService();
		return employeePayrollService;
	}
	private Connection getConnection() throws SQLException {

		String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false";
		String userName = "root";
		String password = "30101999";
		Connection connection;

		System.out.println("Connecting to the database : "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is Succcessfully Established!! "+connection);

		return connection;
	}
	private List<EmployeePayrollDetails> getEmployeePayrollData(ResultSet resultSet) {

		List<EmployeePayrollDetails> employeePayrollList = new ArrayList<>();

		try {
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double basicSalary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollDetails(id, name, basicSalary, startDate));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;

	}
	public List<EmployeePayrollDetails> getEmployeePayrollData(String name) {

		List<EmployeePayrollDetails> employeePayrollList = null;
		if(this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1,name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public List<EmployeePayrollDetails> readData(){

		String sqlStatement = "SELECT emp_id, emp_name, basic_pay, start FROM employee JOIN payroll ON employee.payroll_id = payroll.payroll_id;";
		List<EmployeePayrollDetails> employeePayrollList = new ArrayList<>();

		try (Connection connection = getConnection();){
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);

			while(resultSet.next()) {
				int id = resultSet.getInt("emp_id");
				String name = resultSet.getString("emp_name");
				double basicSalary = resultSet.getDouble("basic_pay");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollDetails(id, name, basicSalary, startDate));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	public int updateEmployeeData(String name, double salary) {

		return this.updateEmployeeDataUsingStatement(name,salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {

		String sqlStatement = String.format("UPDATE payroll ,employee SET net_pay = %2f WHERE employee.payroll_id = payroll.payroll_id AND emp_name = '%s';", salary, name);

		try (Connection connection = getConnection()){
			java.sql.Statement statement = connection.createStatement();
			return statement.executeUpdate(sqlStatement);
		}
		catch(SQLException e){
			e.printStackTrace();
		}		
		return 0;
	}
	private void prepareStatementForEmployeeData() {

		try {
			Connection connection = this.getConnection();
			String sqlStatement = "SELECT * FROM employee,payroll WHERE employee.payroll_id = payroll.payroll.id AND name = ?;";
			employeePayrollDataStatement = connection.prepareStatement(sqlStatement);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}