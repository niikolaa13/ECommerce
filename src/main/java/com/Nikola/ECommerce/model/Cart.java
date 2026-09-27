package com.Nikola.ECommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Cart extends BaseEntity{

	
	@OneToOne
	private User user;
	
	public Cart()
	{
		
	}



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
