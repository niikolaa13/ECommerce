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
import com.Nikola.ECommerce.model.Product;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	
	@Autowired
	ProductRepository repo;
	
	@Autowired
	CategoryRepository catrepo;
	
	
	@GetMapping("/products")
	public List<Product> getUsers()
	{
		return repo.findAll();
	}
	
	@GetMapping("/products/{id}")
	public Product getUsers(@PathVariable("id")int id)
	{
		return repo.findById(id).orElseThrow();
	}
	
	@PostMapping("products")
	public Product addUser(@Valid @RequestBody CreateProductRequest userrequ)
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
	
	@PutMapping("products/{id}")
	public Product updateUser(@PathVariable("id")int id, @RequestBody Product userrequ )
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
	
	@DeleteMapping("/products/{id}")
	public Product deleteUser(@PathVariable("id") int id)
	{
		Product category = repo.findById(id).orElseThrow();
		
		repo.deleteById(id);
		
		return category;
	}
	
}
