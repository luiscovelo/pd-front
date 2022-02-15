package br.com.desafiopd.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import br.com.desafiopd.model.entities.Squad;
import br.com.desafiopd.service.SquadService;

@Service
public class SquadServiceImpl implements SquadService {

	@Override
	public List<Squad> findAll() {

		List<Squad> response = new ArrayList<>();
		String endpoint = "http://localhost:8080/squad";

		RestTemplate restTemplate = new RestTemplate();

		try {

			HttpEntity<String> requestEntity = new HttpEntity<String>("");

			ResponseEntity<Squad[]> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity,
					Squad[].class);

			if (requestResponse.getStatusCodeValue() == 200) {

				Squad[] squads = requestResponse.getBody();
				response = Arrays.asList(squads);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return response;

	}

	@Override
	public Squad findById(Long id) {

		Squad response = new Squad();
		String endpoint = "http://localhost:8080/squad/" + id;

		RestTemplate restTemplate = new RestTemplate();

		try {

			HttpEntity<String> requestEntity = new HttpEntity<String>("");

			ResponseEntity<Squad> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity,
					Squad.class);

			if (requestResponse.getStatusCodeValue() == 200) {

				Squad squad = requestResponse.getBody();
				response = squad;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return response;

	}

	@Override
	public int getCountEmployees(Long id) {

		int response = 0;
		String endpoint = "http://localhost:8080/squad/" + id + "/get-count-employees";

		RestTemplate restTemplate = new RestTemplate();

		try {

			HttpEntity<String> requestEntity = new HttpEntity<String>("");

			ResponseEntity<Integer> requestResponse = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity,
					Integer.class);

			if (requestResponse.getStatusCodeValue() == 200) {

				int total = requestResponse.getBody();
				response = total;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return response;

	}

	@Override
	public Object create(Squad squad) throws JsonMappingException, JsonProcessingException {
		
		ResponseErrorDto responseError = new ResponseErrorDto();
		String endpoint = "http://localhost:8080/squad";

		try {
			
			RestTemplate restTemplate = new RestTemplate();

			Map<String, Object> map = new HashMap<>();

			map.put("name", squad.getName());

			HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map);

			ResponseEntity<Map<String, String>> requestResponse = restTemplate.exchange(endpoint, HttpMethod.POST,
					requestEntity, new ParameterizedTypeReference<Map<String, String>>() {
					});
			
			Object response = requestResponse.getBody();
			
			if (requestResponse.getStatusCodeValue() == 200) {				
				return new ObjectMapper().convertValue(response, Squad.class);
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
