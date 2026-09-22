package com.Nikola.ECommerce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Nikola.ECommerce.Repository.CategoryRepository;
import com.Nikola.ECommerce.Repository.ProductRepository;
import com.Nikola.ECommerce.Requests.CreateProductRequest;
import com.Nikola.ECommerce.Services.ProductService;
import com.Nikola.ECommerce.model.Product;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	
	@Autowired
	ProductService service;
	
	
	@GetMapping("/products")
	public List<Product> getUsers()
	{
		return service.getUsers();
	}
	
	@GetMapping("/products/{id}")
	public Product getUsers(@PathVariable("id")int id)
	{
		return service.getUsers(id);
	}
	
	@PostMapping("products")
	public Product addUser(@Valid @RequestBody CreateProductRequest userrequ)
	{
	
		
		return service.addUser(userrequ);
	}
	
	@PutMapping("products/{id}")
	public Product updateUser(@PathVariable("id")int id, @RequestBody Product userrequ )
	{
		
		
		
		
		return service.updateUser(id, userrequ);
		
	}
	
	@DeleteMapping("/products/{id}")
	public Product deleteUser(@PathVariable("id") int id)
	{
		
		
		return service.deleteUser(id);
	}
	
}
