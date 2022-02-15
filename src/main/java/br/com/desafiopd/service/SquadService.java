package br.com.desafiopd.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.desafiopd.model.entities.Squad;

public interface SquadService {
	
	public List<Squad> findAll();
	
	public Squad findById(Long id);
	
	public int getCountEmployees(Long id);
	
	public Object create(Squad squad) throws JsonMappingException, JsonProcessingException;
	
}
