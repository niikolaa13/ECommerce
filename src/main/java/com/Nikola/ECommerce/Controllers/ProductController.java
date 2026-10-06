package com.Nikola.ECommerce.Controllers;

import java.util.List;

import com.Nikola.ECommerce.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.Nikola.ECommerce.DTO.CreateProductRequest;
import com.Nikola.ECommerce.Services.ProductService;
import com.Nikola.ECommerce.model.Product;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	

	private final ProductService service;

	public ProductController(ProductService service)
	{
		this.service = service;
	}
	
	
	@GetMapping("/products")
	public List<Product> getUsers(@RequestParam(required = false) String name,
								  @RequestParam(required = false) Float minPrice,
								  @RequestParam(required = false) Float maxPrice,
								  @RequestParam(required = false) String sort,
								  @RequestParam(required = false) String direction)
	{

		return service.dynamicSearch(name,minPrice,maxPrice,sort,direction);

	}
	
	@GetMapping("/products/{id}")
	public Product getUsers(@PathVariable("id")int id)
	{

		return service.getProduct(id);
	}


	@GetMapping("/products/expensive")
	public List<Product> getProductsExpensiveThan(@RequestParam("price") Float price)
	{
		return service.getProductsExpensiveThan(price);
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("products")
	public ResponseEntity<Product> addUser(@Valid @RequestBody CreateProductRequest userrequ) throws  Exception
	{
	
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(service.addProduct(userrequ));
	}
	
	@PutMapping("products/{id}")
	public Product updateUser(@PathVariable("id")int id, @RequestBody Product userrequ )throws Exception
	{
		
		
		
		
		return service.updateUser(id, userrequ);
		
	}
	
	@DeleteMapping("/products/{id}")
	public Product deleteUser(@PathVariable("id") int id) throws ResourceNotFoundException {
		
		
		return service.deleteUser(id);
	}
	
}
