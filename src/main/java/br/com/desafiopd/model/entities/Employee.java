package br.com.desafiopd.model.entities;

public class Employee {

	private Long id;
	private String name;
	private int estimatedHours;
	private Long squadId;
	private Squad squad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(int estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Long getSquadId() {
		return squadId;
	}

	public void setSquadId(Long squadId) {
		this.squadId = squadId;
	}

	public Squad getSquad() {
		return squad;
	}

	public void setSquad(Squad squad) {
		this.squad = squad;
	}

}
