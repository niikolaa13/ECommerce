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
import com.Nikola.ECommerce.Requests.CreateCategoryRequest;
import com.Nikola.ECommerce.Requests.CreateUserRequest;
import com.Nikola.ECommerce.model.Category;
import com.Nikola.ECommerce.model.User;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

	@Autowired
	CategoryRepository repo;
	
	
	@GetMapping("/categories")
	public List<Category> getUsers()
	{
		return repo.findAll();
	}
	
	@GetMapping("/categories/{id}")
	public Category getUsers(@PathVariable("id")int id)
	{
		return repo.findById(id).orElseThrow();
	}
	
	@PostMapping("categories")
	public Category addUser(@Valid @RequestBody CreateCategoryRequest userrequ)
	{
		Category category = new Category();
		category.setName(userrequ.getName());
		
		repo.save(category);
		
		return category;
	}
	
	@PutMapping("categories/{id}")
	public Category updateUser(@PathVariable("id")int id, @RequestBody Category user1 )
	{
		Category category = repo.findById(id).orElseThrow();
		
		category.setName(user1.getName());
		
		return category;
		
	}
	
	@DeleteMapping("/categories/{id}")
	public Category deleteUser(@PathVariable("id") int id)
	{
		Category category = repo.findById(id).orElseThrow();
		
		repo.deleteById(id);
		
		return category;
	}
	
}
