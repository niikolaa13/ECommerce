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

import com.Nikola.ECommerce.Repository.CartRepository;
import com.Nikola.ECommerce.Repository.Cart_itemRepository;
import com.Nikola.ECommerce.Repository.ProductRepository;
import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.Requests.CreateCart_ItemRequest;
import com.Nikola.ECommerce.Requests.UpdateQuantity;
import com.Nikola.ECommerce.Services.CartService;
import com.Nikola.ECommerce.model.Cart;
import com.Nikola.ECommerce.model.Cart_item;
import com.Nikola.ECommerce.model.Product;

import jakarta.validation.Valid;

@RestController
public class CartController {


	
	@Autowired 
	CartService service;
	
	@GetMapping("/users/{userId}/cart")
	public List<Cart_item> getCart(@PathVariable("userId")int id)
	{	
		return service.getCart(id);
		
	}
	
	@PostMapping("/users/{userId}/cart")
	public Cart_item addInCart(@Valid @RequestBody CreateCart_ItemRequest cartItem,@PathVariable("userId")int id)
	{
		
		return service.addInCart(cartItem, id);
		
		
		
	}
	
	@PutMapping("/users/{userId}/cart/items/{itemId}")
	public Cart_item azurirajKorpu(@PathVariable("userId") int userId,@PathVariable("itemId") int itemId,@RequestBody UpdateQuantity quantity)
	{
		
		return service.azurirajKorpu(userId, itemId, quantity);
		
		
	}
	
	@DeleteMapping("/users/{userId}/cart/items/{itemId}")
	public void obrisiItemUKorpi(@PathVariable("userId") int userId,@PathVariable("itemId") int itemId,@RequestBody UpdateQuantity quantity)
	{
		
		service.obrisiItemUKorpi(userId, itemId, quantity);
		
	}
	
	
}
