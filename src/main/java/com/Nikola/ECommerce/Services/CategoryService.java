package com.Nikola.ECommerce.Services;

import java.util.List;

import com.Nikola.ECommerce.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.Nikola.ECommerce.DTO.CreateCategoryRequest;
import com.Nikola.ECommerce.Repository.CategoryRepository;
import com.Nikola.ECommerce.model.Category;

@Service
public class CategoryService {

	
	private final CategoryRepository repo;
	
	public CategoryService(CategoryRepository repo)
	{

		this.repo = repo;
	}
	
	@GetMapping("/categories")
	public List<Category> getUsers()
	{
		return repo.findAll();
	}
	
	
	public Category getUsers(int id) throws ResourceNotFoundException {
		return repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ne postoji Kategorija sa ID:"+id));
	}
	
	
	public Category addUser( CreateCategoryRequest userrequ)
	{
		Category category = new Category();
		category.setName(userrequ.getName());
		
		repo.save(category);
		
		return category;
	}
	
	
	public Category updateUser(int id,  Category user1 ) throws Exception {
		Category category = repo.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji Kategorija sa ID:"+id));
		
		category.setName(user1.getName());
		
		return category;
		
	}
	
	
	public Category deleteUser( int id) throws  Exception
	{
		Category category = repo.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji Kategorija sa ID:"+id));
		
		repo.deleteById(id);
		
		return category;
	}
	
}
