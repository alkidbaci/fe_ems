package com.alkid.app.Thymeleaf.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alkid.app.Thymeleaf.entity.Employee;

@Service
public class EmployeeServiceRestClientImpl implements EmployeeService {

	private RestTemplate restTemplate;

	private String crmRestUrl;
		
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	public EmployeeServiceRestClientImpl(RestTemplate theRestTemplate, 
										@Value("${crm.rest.url}") String theUrl) {
		restTemplate = theRestTemplate;
		crmRestUrl = theUrl;
				
		logger.info("Loaded property:  crm.rest.url=" + crmRestUrl);
	}
	
	@Override
	public List<Employee> getEmployee() {
		
		logger.info("in getEmployees(): Calling REST API " + crmRestUrl);

		// make REST call
		ResponseEntity<List<Employee>> responseEntity = 
											restTemplate.exchange(crmRestUrl, HttpMethod.GET, null, 
																  new ParameterizedTypeReference<List<Employee>>() {});

		// get the list of employees from response
		List<Employee> employees = responseEntity.getBody();

		logger.info("in getEmployees(): customers" + employees);
		
		return employees;
	}

	@Override
	public Employee getEmployee(int theId) {

		logger.info("in getEmployee(): Calling REST API " + crmRestUrl);

		// make REST call
		Employee theEmployee = 
				restTemplate.getForObject(crmRestUrl + "/" + theId, 
						Employee.class);

		logger.info("in saveEmployee(): theEmployee=" + theEmployee);
		
		return theEmployee;
	}

	@Override
	public void saveEmployee(Employee theEmployee) {

		logger.info("in saveEmployee(): Calling REST API " + crmRestUrl);
		
		int employeeId = theEmployee.getId();

		// make REST call
		if (employeeId == 0) {
			// add employee
			restTemplate.postForEntity(crmRestUrl, theEmployee, String.class);			
		
		} else {
			// update employee
			restTemplate.put(crmRestUrl, theEmployee);
		}

		logger.info("in saveEmployee(): success");	
	}

	@Override
	public void deleteEmployee(int theId) {

		logger.info("in deleteEmoployee(): Calling REST API " + crmRestUrl);

		// make REST call
		restTemplate.delete(crmRestUrl + "/" + theId);

		logger.info("in deleteEmployee(): deleted employee theId=" + theId);
	}

}
