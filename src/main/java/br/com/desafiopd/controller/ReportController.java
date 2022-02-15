package br.com.desafiopd.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.desafiopd.model.dto.ResponseErrorDto;
import br.com.desafiopd.model.entities.Report;
import br.com.desafiopd.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@PostMapping("/create")
	public String createReport(HttpServletRequest request, @ModelAttribute Report report, RedirectAttributes redirAttrs) throws JsonMappingException, JsonProcessingException {
		
		String referer = request.getHeader("Referer");
		String redirect = referer;
		
		boolean pathWithParams = redirect.contains("?");
		
		if(pathWithParams) {
			String[] path = redirect.split("\\?");
			redirect = path[0];
		}
		
		Object response = reportService.create(report);
		
		if(response instanceof ResponseErrorDto) {
			
			ResponseErrorDto errors = (ResponseErrorDto) response;
			
			redirAttrs.addFlashAttribute("reportInvalidInput", errors.getErrors());
			redirect = redirect + "?modal=report";
			
		}
		
		return "redirect:" + redirect ;
		
	}
	
}
