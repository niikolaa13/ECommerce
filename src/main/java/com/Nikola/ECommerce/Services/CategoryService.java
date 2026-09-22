package com.Nikola.ECommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.Nikola.ECommerce.Repository.CategoryRepository;
import com.Nikola.ECommerce.Requests.CreateCategoryRequest;
import com.Nikola.ECommerce.model.Category;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repo;
	
	
	@GetMapping("/categories")
	public List<Category> getUsers()
	{
		return repo.findAll();
	}
	
	
	public Category getUsers(int id)
	{
		return repo.findById(id).orElseThrow();
	}
	
	
	public Category addUser( CreateCategoryRequest userrequ)
	{
		Category category = new Category();
		category.setName(userrequ.getName());
		
		repo.save(category);
		
		return category;
	}
	
	
	public Category updateUser(int id,  Category user1 )
	{
		Category category = repo.findById(id).orElseThrow();
		
		category.setName(user1.getName());
		
		return category;
		
	}
	
	
	public Category deleteUser( int id)
	{
		Category category = repo.findById(id).orElseThrow();
		
		repo.deleteById(id);
		
		return category;
	}
	
}
