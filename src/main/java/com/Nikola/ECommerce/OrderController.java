package com.Nikola.ECommerce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nikola.ECommerce.Services.OrderService;
import com.Nikola.ECommerce.model.Orders;

@RestController
public class OrderController {

	@Autowired
	OrderService service;
	
	@PostMapping("/users/{userId}/orders")
	@Transactional
	public Orders addOrder(@PathVariable("userId") int userId) {

	    return service.addOrder(userId);
	}
	
	@GetMapping("/orders")
	public List<Orders> getAllOrders() {
		
	    return service.getAllOrders();	
	    
	}
	
	@GetMapping("/orders/{userId}")
	public List<Orders> getOrdersByUser(@PathVariable("userId")int userId)
	{
		
		return service.getOrdersByUser(userId);
		
	}
	
}
