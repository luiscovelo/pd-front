package br.com.desafiopd.model.dto;

import java.time.LocalDate;

public class ReportBySquadDto {

	private String employee;
	private String description;
	private LocalDate createdAt;
	private int spentHours;

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public int getSpentHours() {
		return spentHours;
	}

	public void setSpentHours(int spentHours) {
		this.spentHours = spentHours;
	}

}
