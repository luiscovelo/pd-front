package br.com.desafiopd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.desafiopd.model.dto.ResponseErrorDto;
import br.com.desafiopd.model.entities.Employee;
import br.com.desafiopd.model.entities.Report;
import br.com.desafiopd.model.entities.Squad;
import br.com.desafiopd.service.EmployeeService;
import br.com.desafiopd.service.SquadService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	SquadService squadService;
	
	@GetMapping
	public String getPageEmployee(Model model) {
		
		List<Employee> employees = employeeService.findAll();
		List<Squad> squads = squadService.findAll();
		
		model.addAttribute("employees", employees);
		model.addAttribute("squads", squads);
		
		Squad squadModel = new Squad();
		Employee employeeModel = new Employee();
		Report reportModel = new Report();
		
		model.addAttribute("squadModel", squadModel);
		model.addAttribute("employeeModel", employeeModel);
		model.addAttribute("reportModel", reportModel);
		
		return "employee";
	}
	
	@PostMapping("/create")
	public String createEmployee(HttpServletRequest request, @ModelAttribute Employee employee, RedirectAttributes redirAttrs) throws JsonMappingException, JsonProcessingException {
		
		String referer = request.getHeader("Referer");
		String redirect = referer;
			
		boolean pathWithParams = redirect.contains("?");
		
		if(pathWithParams) {
			String[] path = redirect.split("\\?");
			redirect = path[0];
		}
		
		Object response = employeeService.create(employee);
		
		if(response instanceof ResponseErrorDto) {
			
			ResponseErrorDto errors = (ResponseErrorDto) response;
			
			redirAttrs.addFlashAttribute("employeeInvalidInput", errors.getErrors());
			redirect = redirect + "?modal=employee";
			
		}
		
		return "redirect:" + redirect ;
		
	}
	
}
