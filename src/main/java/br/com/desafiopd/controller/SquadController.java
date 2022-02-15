package br.com.desafiopd.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.desafiopd.model.dto.ReportBySquadDto;
import br.com.desafiopd.model.dto.ReportDateDto;
import br.com.desafiopd.model.dto.ReportSquadAverageHoursPerDayDto;
import br.com.desafiopd.model.dto.ReportSquadHourDto;
import br.com.desafiopd.model.dto.ResponseErrorDto;
import br.com.desafiopd.model.entities.Employee;
import br.com.desafiopd.model.entities.Report;
import br.com.desafiopd.model.entities.Squad;
import br.com.desafiopd.service.ReportService;
import br.com.desafiopd.service.SquadService;

@Controller
@RequestMapping("/squad")
public class SquadController {

	@Autowired
	SquadService squadService;
	
	@Autowired
	ReportService reportService;
	
	@GetMapping
	public String getPageSquad(Model model) {
		
		List<Squad> squads = squadService.findAll();
		
		model.addAttribute("squads", squads);
		
		return "squad";
	}
	
	@GetMapping("/detail/{id}")
	public String getPageSquadDetail(@PathVariable Long id, Model model) {
		
		ReportSquadHourDto reportSquadHour = new ReportSquadHourDto();
		ReportSquadAverageHoursPerDayDto reportSquadAverageHours = new ReportSquadAverageHoursPerDayDto();
		ReportDateDto reportDateDto = new ReportDateDto();
		List<ReportBySquadDto> reports = new ArrayList<>();
		int totalEmployees = squadService.getCountEmployees(id);
		
		Squad squad = squadService.findById(id);
		
		model.addAttribute("squad", squad);
		model.addAttribute("reportDateDto", reportDateDto);
		model.addAttribute("reportSquadHour", reportSquadHour);
		model.addAttribute("reportSquadAverageHours", reportSquadAverageHours);
		model.addAttribute("reports", reports);
		model.addAttribute("totalEmployees", totalEmployees);
		
		Squad squadModel = new Squad();
		Employee employeeModel = new Employee();
		Report reportModel = new Report();
		
		model.addAttribute("squadModel", squadModel);
		model.addAttribute("employeeModel", employeeModel);
		model.addAttribute("reportModel", reportModel);
		
		return "squad_detail";
	}
	
	@PostMapping("/detail/{id}")
	public String postPageSquadDetail(@ModelAttribute ReportDateDto reportDateDto, @PathVariable Long id, Model model) {
		
		String initialDate = reportDateDto.getInitialDate();
		String finalDate = reportDateDto.getFinalDate();
		
		Squad squad = squadService.findById(id);
		ReportSquadHourDto reportSquadHour = reportService.getTotalHoursSquad(id, initialDate, finalDate);
		ReportSquadAverageHoursPerDayDto reportSquadAverageHours = reportService.getAverageHoursSquadPerDay(id, initialDate, finalDate);
		List<ReportBySquadDto> reports = reportService.getReportsBySquadBetweenDate(id, initialDate, finalDate);
		int totalEmployees = squadService.getCountEmployees(id);
		
		model.addAttribute("reportSquadHour", reportSquadHour);
		model.addAttribute("reportSquadAverageHours", reportSquadAverageHours);
		model.addAttribute("reports", reports);
		model.addAttribute("squad", squad);
		model.addAttribute("totalEmployees", totalEmployees);
		
		Squad squadModel = new Squad();
		Employee employeeModel = new Employee();
		Report reportModel = new Report();
		
		model.addAttribute("squadModel", squadModel);
		model.addAttribute("employeeModel", employeeModel);
		model.addAttribute("reportModel", reportModel);
		
		return "squad_detail";
		
	}
	
	@PostMapping("/create")
	public String createSquad(HttpServletRequest request, @ModelAttribute Squad squad, RedirectAttributes redirAttrs) throws JsonMappingException, JsonProcessingException {
		
		String referer = request.getHeader("Referer");
		String redirect = referer;
		
		boolean pathWithParams = redirect.contains("?");
		
		if(pathWithParams) {
			String[] path = redirect.split("\\?");
			redirect = path[0];
		}
		
		Object response = squadService.create(squad);
		
		if(response instanceof ResponseErrorDto) {
			
			ResponseErrorDto errors = (ResponseErrorDto) response;
			
			redirAttrs.addFlashAttribute("squadInvalidInput", errors.getErrors());
			redirect = redirect + "?modal=squad";
			
		}
		
		return "redirect:" + redirect ;
		
	}
	
}
