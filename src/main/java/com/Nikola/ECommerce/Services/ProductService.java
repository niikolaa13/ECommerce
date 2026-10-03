package com.Nikola.ECommerce.Services;

import java.util.List;
import java.util.Optional;

import com.Nikola.ECommerce.Exceptions.ProductNotFoundException;
import com.Nikola.ECommerce.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Nikola.ECommerce.DTO.CreateProductRequest;
import com.Nikola.ECommerce.Repository.CategoryRepository;
import com.Nikola.ECommerce.Repository.ProductRepository;
import com.Nikola.ECommerce.model.Product;

@Service
public class ProductService {

	
	private final ProductRepository repo;
	private final CategoryRepository catrepo;
	
	
	public ProductService(ProductRepository repo, CategoryRepository catrepo)
	{
		this.repo = repo;
		this.catrepo = catrepo;
	}
	
	
	public List<Product> getProducts()
	{

		return repo.findAll();
	}


	public ResponseEntity<Product> getProduct(int id) {


		Product product = repo.findById(id).orElseThrow(()->
				new ProductNotFoundException("proizvod sa ID:"+ id +" ne postoji"));

		return ResponseEntity.ok(product);
	}
	
	
	public Product addProduct( CreateProductRequest userrequ) throws  Exception
	{
		Product product = new Product();
		product.setName(userrequ.getName());
		product.setText(userrequ.getText());
		product.setPrice(userrequ.getPrice());
		product.setStock(userrequ.getStock());
		product.setCategory(catrepo.findById(userrequ.getCategoryID()).orElseThrow(
				()-> new ResourceNotFoundException("Kategorija sa ID:"+ catrepo.findById(userrequ.getCategoryID())+" ne postoji")
		));
		
		repo.save(product);
		
		return product;
	}
	
	
	public Product updateUser(int id,  Product userrequ )throws  Exception
	{
		Product product = repo.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji produkt sa tim ID-jem"));
		
		if(userrequ.getName()!= null)
		product.setName(userrequ.getName());
		if(userrequ.getText()!= null)
		product.setText(userrequ.getText());
		if(userrequ.getPrice()!= 0)
		product.setPrice(userrequ.getPrice());
		if(userrequ.getStock()!= 0)
		product.setStock(userrequ.getStock());
		
		repo.save(product);
		
		
		
		return product;
		
	}
	
	
	public Product deleteUser( int id) throws ResourceNotFoundException {
		Product category = repo.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji produkt sa tim ID-jem"));
		
		repo.deleteById(id);
		
		return category;
	}
	
}
