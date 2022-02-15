package br.com.desafiopd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.desafiopd.model.entities.Report;

@Controller
public class HomeController {
	
	@GetMapping
	public String getPageHome(Model model) {
		
		Report reportModel = new Report();

		model.addAttribute("reportModel", reportModel);
		
		return "index";
	}
	
}
