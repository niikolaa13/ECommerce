package com.Nikola.ECommerce.Services;


import java.util.List;


import com.Nikola.ECommerce.Exceptions.ProductNotFoundException;
import com.Nikola.ECommerce.Exceptions.ResourceNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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


	public Product getProduct(int id) {


		Product product = repo.findById(id).orElseThrow(()->
				new ProductNotFoundException("proizvod sa ID:"+ id +" ne postoji"));

		return product;
	}

	public List<Product> getProductsByName(String name)
	{
		return repo.findAllByNameContainingIgnoreCase(name);
	}

	public List<Product> getProductsByPriceAndName(Float minPrice, Float maxPrice,String name) {



		if(minPrice!= null && maxPrice != null)
		{
			return repo.findAllByNameContainingIgnoreCaseAndPriceBetween(name,minPrice,maxPrice);

        }

		if(minPrice != null)
		{
			return repo.findAllByNameContainingIgnoreCaseAndPriceGreaterThanEqual(name,minPrice);
		}

		return  repo.findAllByNameContainingIgnoreCaseAndPriceLessThanEqual(name,maxPrice);

	}

	public List<Product> getProductsBySortAndDirection(String sort,String direction)
	{

		Sort sort1;

		if (direction == null || "asc".equalsIgnoreCase(direction)) {
			sort1 = Sort.by(sort).ascending();
		} else {
			sort1 = Sort.by(sort).descending();
		}

		return repo.findAll(sort1);

	}

	public List<Product> getProductsBySortAndFilter(String name, Float minPrice, Float maxPrice, String sort, String direction) {


		Sort sort1;

		if (direction == null || "asc".equalsIgnoreCase(direction)) {
			sort1 = Sort.by(sort).ascending();
		} else {
			sort1 = Sort.by(sort).descending();
		}

		return repo.findAllByNameContainingIgnoreCaseAndPriceBetween(name,minPrice,maxPrice,sort1);

	}


	public List<Product> getProductsExpensiveThan(Float price)
	{
		return repo.findProductsExpensiveThan(price);
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


	public List<Product> dynamicSearch(String name, Float minPrice,Float maxPrice, String sort, String direction)
	{
		Specification<Product> spec = ((root, query, criteriaBuilder) ->
				criteriaBuilder.conjunction());

		if(name != null)
		{
			spec = spec.and(((root, query, criteriaBuilder) ->
					criteriaBuilder.like(
							criteriaBuilder.lower(root.get("name")),
							"%"+ name.toLowerCase() + "%"
					)));
		}

		if(minPrice!= null)
		{
			spec = spec.and(((root, query, criteriaBuilder) ->
					criteriaBuilder.greaterThanOrEqualTo(root.get("price"),
							minPrice)));
		}

		if(maxPrice!= null)
		{
			spec = spec.and(((root, query, criteriaBuilder) ->
					criteriaBuilder.lessThanOrEqualTo(root.get("price"),
							maxPrice)));
		}

		Sort sort1 = null;

		if(sort != null){

			if("asc".equalsIgnoreCase(direction) || direction == null){

				sort1 = Sort.by(sort).ascending();

			}
			if("desc".equalsIgnoreCase(direction)){

				sort1 =Sort.by(sort).descending();

			}
		}

		if(sort1 == null)
		{
			return repo.findAll(spec);
		}

		return repo.findAll(spec,sort1);

	}

}
