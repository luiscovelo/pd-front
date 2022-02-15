package br.com.desafiopd.model.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Report {

	private Long id;
	private String description;
	private int spentHours;
	private Long employeeId;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSpentHours() {
		return spentHours;
	}

	public void setSpentHours(int spentHours) {
		this.spentHours = spentHours;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

}
