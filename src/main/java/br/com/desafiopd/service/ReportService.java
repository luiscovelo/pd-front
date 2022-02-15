package br.com.desafiopd.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.desafiopd.model.dto.ReportBySquadDto;
import br.com.desafiopd.model.dto.ReportEmployeeHourDto;
import br.com.desafiopd.model.dto.ReportSquadAverageHoursPerDayDto;
import br.com.desafiopd.model.dto.ReportSquadHourDto;
import br.com.desafiopd.model.entities.Report;

public interface ReportService {
	
	public List<ReportEmployeeHourDto> getTotalHoursSquadMember(Long squadId, String initialDate, String finalDate);
	
	public ReportSquadHourDto getTotalHoursSquad(Long squadId, String initialDate, String finalDate);
	
	public ReportSquadAverageHoursPerDayDto getAverageHoursSquadPerDay(Long squadId, String initialDate, String finalDate);
	
	public List<ReportBySquadDto> getReportsBySquadBetweenDate(Long squaId, String initialDate, String finalDate);
	
	public Object create(Report report) throws JsonMappingException, JsonProcessingException;
	
}
