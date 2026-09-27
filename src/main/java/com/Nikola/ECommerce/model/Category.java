package com.Nikola.ECommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category extends BaseEntity{

	
	private String name;
	
	public Category()
	{
		
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
