package com.Nikola.ECommerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Nikola.ECommerce.DTO.CreateCategoryRequest;
import com.Nikola.ECommerce.Services.CategoryService;
import com.Nikola.ECommerce.model.Category;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

	@Autowired
	CategoryService service;
	
	
	@GetMapping("/categories")
	public List<Category> getUsers()
	{
		return service.getUsers();
	}
	
	@GetMapping("/categories/{id}")
	public Category getUsers(@PathVariable("id")int id) throws  Exception
	{
		return service.getUsers(id);
	}
	
	@PostMapping("categories")
	public Category addUser(@Valid @RequestBody CreateCategoryRequest userrequ)
	{
		return service.addUser(userrequ);
	}
	
	@PutMapping("categories/{id}")
	public Category updateUser(@PathVariable("id")int id, @RequestBody Category user1 ) throws  Exception
	{
		return service.updateUser(id, user1);
		
	}
	
	@DeleteMapping("/categories/{id}")
	public Category deleteUser(@PathVariable("id") int id) throws  Exception
	{
		return service.deleteUser(id);
	}
	
}
