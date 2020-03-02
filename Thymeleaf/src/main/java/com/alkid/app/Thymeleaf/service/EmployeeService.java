package com.alkid.app.Thymeleaf.service;

import java.util.List;

import com.alkid.app.Thymeleaf.entity.Employee;

public interface EmployeeService {

	public List<Employee> getEmployee();

	public void saveEmployee(Employee theCustomer);

	public Employee getEmployee(int theId);

	public void deleteEmployee(int theId);
	
}
