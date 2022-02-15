package br.com.desafiopd.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.desafiopd.model.entities.Employee;

public interface EmployeeService {
	
	public List<Employee> findAll();
	
	public Object create(Employee employee) throws JsonMappingException, JsonProcessingException;
	
}
