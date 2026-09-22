package com.Nikola.ECommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nikola.ECommerce.Repository.CategoryRepository;
import com.Nikola.ECommerce.Repository.ProductRepository;
import com.Nikola.ECommerce.Requests.CreateProductRequest;
import com.Nikola.ECommerce.model.Product;

@Service
public class ProductService {

	
	@Autowired
	ProductRepository repo;
	
	@Autowired
	CategoryRepository catrepo;
	
	
	
	public List<Product> getUsers()
	{
		return repo.findAll();
	}
	
	
	public Product getUsers(int id)
	{
		return repo.findById(id).orElseThrow();
	}
	
	
	public Product addUser( CreateProductRequest userrequ)
	{
		Product product = new Product();
		product.setName(userrequ.getName());
		product.setText(userrequ.getText());
		product.setPrice(userrequ.getPrice());
		product.setStock(userrequ.getStock());
		product.setCategory(catrepo.findById(userrequ.getCategoryID()).orElseThrow());
		
		repo.save(product);
		
		return product;
	}
	
	
	public Product updateUser(int id,  Product userrequ )
	{
		Product product = repo.findById(id).orElseThrow();
		
		if(userrequ.getName()!= null)
		product.setName(userrequ.getName());
		if(userrequ.getText()!= null)
		product.setText(userrequ.getText());
		if(userrequ.getPrice()!= 0)
		product.setPrice(userrequ.getPrice());
		if(userrequ.getStock()!= 0)
		product.setStock(userrequ.getStock());
		
		
		
		return product;
		
	}
	
	
	public Product deleteUser( int id)
	{
		Product category = repo.findById(id).orElseThrow();
		
		repo.deleteById(id);
		
		return category;
	}
	
}
