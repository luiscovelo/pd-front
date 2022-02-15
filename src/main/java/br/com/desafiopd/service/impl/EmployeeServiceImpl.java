package br.com.desafiopd.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafiopd.model.dto.ResponseErrorDto;
import br.com.desafiopd.model.entities.Employee;
import br.com.desafiopd.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public List<Employee> findAll() {
		List<Employee> response = new ArrayList<>();
		String endpoint = "http://localhost:8080/employee";

		RestTemplate restTemplate = new RestTemplate();

		try {

			HttpEntity<String> requestEntity = new HttpEntity<String>("");

			ResponseEntity<Employee[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity,
					Employee[].class);

			if (requestResponse.getStatusCodeValue() == 200) {

				Employee[] employees = requestResponse.getBody();
				response = Arrays.asList(employees);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return response;
		
	}

	@Override
	public Object create(Employee employee) throws JsonMappingException, JsonProcessingException {
		
		ResponseErrorDto responseError = new ResponseErrorDto();
		String endpoint = "http://localhost:8080/employee";

		try {
			
			RestTemplate restTemplate = new RestTemplate();

			Map<String, Object> map = new HashMap<>();

			map.put("name", employee.getName());
			map.put("estimatedHours", employee.getEstimatedHours());
			map.put("squadId", employee.getSquadId());

			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);

			ResponseEntity<Map<String, String>> requestResponse = restTemplate.exchange(endpoint, HttpMethod.POST,
					requestEntity, new ParameterizedTypeReference<Map<String, String>>() {
					});
			
			Object response = requestResponse.getBody();
			
			if (requestResponse.getStatusCodeValue() == 200) {
				return new ObjectMapper().convertValue(response, Employee.class);
			}
			
		} catch (HttpClientErrorException error) {
			
			Map<String, String> errorMap = new ObjectMapper().readValue(error.getResponseBodyAsString(), Map.class);
			
			responseError.setErrors(errorMap);
			
		} catch (Exception e) {
			
			Map<String, String> errors = new HashMap<>();
			errors.put("error", e.getMessage());
			
			responseError.setErrors(errors);
			
		}
		
		return responseError;
		
	}

}
