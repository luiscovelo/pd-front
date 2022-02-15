package br.com.desafiopd.model.dto;

import java.util.Map;

public class ResponseErrorDto {

	private Map<String, String> errors;

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
