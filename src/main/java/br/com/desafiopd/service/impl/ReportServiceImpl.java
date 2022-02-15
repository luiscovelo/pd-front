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

import br.com.desafiopd.model.dto.ReportBySquadDto;
import br.com.desafiopd.model.dto.ReportEmployeeHourDto;
import br.com.desafiopd.model.dto.ReportSquadAverageHoursPerDayDto;
import br.com.desafiopd.model.dto.ReportSquadHourDto;
import br.com.desafiopd.model.dto.ResponseErrorDto;
import br.com.desafiopd.model.entities.Employee;
import br.com.desafiopd.model.entities.Report;
import br.com.desafiopd.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	@Override
	public List<ReportEmployeeHourDto> getTotalHoursSquadMember(Long squadId, String initialDate, String finalDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReportSquadHourDto getTotalHoursSquad(Long squadId, String initialDate, String finalDate) {
		
		ReportSquadHourDto response = new ReportSquadHourDto();
		String endpoint = "http://localhost:8080/report/get-total-hours-squad?squadId="+squadId+"&initialDate="+initialDate+"&finalDate="+finalDate;

		RestTemplate restTemplate = new RestTemplate();
		
		try {
			
			HttpEntity<String> requestEntity = new HttpEntity<String>("");
			
			ResponseEntity<ReportSquadHourDto> requestResponse = restTemplate.exchange(
				endpoint, 
				HttpMethod.GET, 
				requestEntity,
				ReportSquadHourDto.class
			);
			
			if(requestResponse.getStatusCodeValue() == 200) {
				
				ReportSquadHourDto report = requestResponse.getBody();
				response = report;
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return response;
		
	}

	@Override
	public ReportSquadAverageHoursPerDayDto getAverageHoursSquadPerDay(Long squadId, String initialDate, String finalDate) {
		
		ReportSquadAverageHoursPerDayDto response = new ReportSquadAverageHoursPerDayDto();
		String endpoint = "http://localhost:8080/report/get-average-hours-squad-per-day?squadId="+squadId+"&initialDate="+initialDate+"&finalDate="+finalDate;

		RestTemplate restTemplate = new RestTemplate();
		
		try {
			
			HttpEntity<String> requestEntity = new HttpEntity<String>("");
			
			ResponseEntity<ReportSquadAverageHoursPerDayDto> requestResponse = restTemplate.exchange(
				endpoint, 
				HttpMethod.GET, 
				requestEntity,
				ReportSquadAverageHoursPerDayDto.class
			);
			
			if(requestResponse.getStatusCodeValue() == 200) {
				
				ReportSquadAverageHoursPerDayDto report = requestResponse.getBody();
				response = report;
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return response;
		
	}

	@Override
	public List<ReportBySquadDto> getReportsBySquadBetweenDate(Long squadId, String initialDate, String finalDate) {
		
		List<ReportBySquadDto> response = new ArrayList<>();
		String endpoint = "http://localhost:8080/report/get-reports-by-squad?squadId="+squadId+"&initialDate="+initialDate+"&finalDate="+finalDate;

		RestTemplate restTemplate = new RestTemplate();
		
		try {
			
			HttpEntity<String> requestEntity = new HttpEntity<String>("");
			
			ResponseEntity<ReportBySquadDto[]> requestResponse = restTemplate.exchange(
				endpoint, 
				HttpMethod.GET, 
				requestEntity,
				ReportBySquadDto[].class
			);
			
			if(requestResponse.getStatusCodeValue() == 200) {
				
				ReportBySquadDto[] report = requestResponse.getBody();
				response = Arrays.asList(report);
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return response;
		
	}

	@Override
	public Object create(Report report) throws JsonMappingException, JsonProcessingException {
		
		ResponseErrorDto responseError = new ResponseErrorDto();
		String endpoint = "http://localhost:8080/report";

		try {
			
			RestTemplate restTemplate = new RestTemplate();

			Map<String, Object> map = new HashMap<>();

			map.put("description", report.getDescription());
			map.put("spentHours", report.getSpentHours());
			map.put("employeeId", report.getEmployeeId());

			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);

			ResponseEntity<Map<String, String>> requestResponse = restTemplate.exchange(endpoint, HttpMethod.POST,
					requestEntity, new ParameterizedTypeReference<Map<String, String>>() {
					});
			
			Object response = requestResponse.getBody();
			
			if (requestResponse.getStatusCodeValue() == 200) {
				return mapper.convertValue(response, Report.class);
			}
			
		} catch (HttpClientErrorException error) {
			
			Map<String, String> errorMap = mapper.readValue(error.getResponseBodyAsString(), Map.class);
			
			responseError.setErrors(errorMap);
			
		} catch (Exception e) {
			
			Map<String, String> errors = new HashMap<>();
			errors.put("error", e.getMessage());
			
			responseError.setErrors(errors);
			
		}
		
		return responseError;
		
	}

}
