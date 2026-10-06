package com.Nikola.ECommerce.Controllers;

import java.util.List;

import com.Nikola.ECommerce.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Nikola.ECommerce.DTO.CreateCart_ItemRequest;
import com.Nikola.ECommerce.DTO.UpdateQuantity;
import com.Nikola.ECommerce.Services.CartService;
import com.Nikola.ECommerce.model.Cart_item;

import jakarta.validation.Valid;

@RestController
public class CartController {


	
	@Autowired 
	CartService service;
	
	@GetMapping("/users/{userId}/cart")
	public ResponseEntity<List<Cart_item>> getCart(@PathVariable("userId")int id) throws ResourceNotFoundException {
		return ResponseEntity.ok(service.getCart(id));
		
	}
	
	@PostMapping("/users/{userId}/cart")
	public ResponseEntity<Cart_item> addInCart(@Valid @RequestBody CreateCart_ItemRequest cartItem,@PathVariable("userId")int id) throws ResourceNotFoundException {
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(service.addInCart(cartItem, id));
		
		
		
	}
	
	@PutMapping("/users/{userId}/cart/items/{itemId}")
	public Cart_item azurirajKorpu(@PathVariable("userId") int userId,@PathVariable("itemId") int itemId,@RequestBody UpdateQuantity quantity) throws ResourceNotFoundException {
		
		return service.azurirajKorpu(userId, itemId, quantity);
		
		
	}
	
	@DeleteMapping("/users/{userId}/cart/items/{itemId}")
	public void obrisiItemUKorpi(@PathVariable("userId") int userId,@PathVariable("itemId") int itemId,@RequestBody UpdateQuantity quantity) throws ResourceNotFoundException {
		
		service.obrisiItemUKorpi(userId, itemId, quantity);
		
	}
	
	
}
