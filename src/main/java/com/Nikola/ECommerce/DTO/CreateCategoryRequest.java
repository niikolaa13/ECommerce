package com.Nikola.ECommerce.DTO;

import jakarta.validation.constraints.NotNull;

public class CreateCategoryRequest {

	private int id;
	@NotNull
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
