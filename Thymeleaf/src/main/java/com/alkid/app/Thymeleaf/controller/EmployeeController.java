package com.alkid.app.Thymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alkid.app.Thymeleaf.entity.Employee;
import com.alkid.app.Thymeleaf.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	// need to inject our employee service
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get employees from the service
		List<Employee> theEmployees = employeeService.getEmployee();
				
		// add the employees to the model
		theModel.addAttribute("employees", theEmployees);
		
		return "employee/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee);
		
		return "employee/addForm";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		
		// save the employee using our service
		employeeService.saveEmployee(theEmployee);	
		
		return "redirect:/employee/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {
		
		// get the employee from our service
		Employee theEmployee = employeeService.getEmployee(theId);	
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		// send over to our form		
		return "employee/addForm";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("employeeId") int theId) {
		
		// delete the customer
		employeeService.deleteEmployee(theId);
		
		return "redirect:/employee/list";
	}
}










