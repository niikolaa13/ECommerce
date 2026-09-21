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
import com.Nikola.ECommerce.model.Cart;
import com.Nikola.ECommerce.model.Cart_item;
import com.Nikola.ECommerce.model.Product;

import jakarta.validation.Valid;

@RestController
public class CartController {

	@Autowired
	CartRepository repo;
	
	@Autowired 
	Cart_itemRepository itemrepo;
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	ProductRepository prodrepo;
	
	@GetMapping("/users/{userId}/cart")
	public List<Cart_item> getCart(@PathVariable("userId")int id)
	{	
		Cart cart;
		if(repo.findByUser_id(id)==null) {
			 cart = new Cart();
			cart.setUser(userrepo.findById(id).orElseThrow());
		}
		else {
			 cart = repo.findByUser_id(id);

		}
		
		return itemrepo.findAllByCart_id(cart.getId());
		
	}
	
	@PostMapping("/users/{userId}/cart")
	public Cart_item addInCart(@Valid @RequestBody CreateCart_ItemRequest cartItem,@PathVariable("userId")int id)
	{
		
		Cart cart;
		if(repo.findByUser_id(id)==null) {
			cart = new Cart();
			cart.setUser(userrepo.findById(id).orElseThrow());
			repo.save(cart);
			
		}
		else {
			cart = repo.findByUser_id(id);
		}
		
		
		
		List<Cart_item> itemi = itemrepo.findAll();
		for(Cart_item stvar :itemi)
		{
			Product prod = stvar.getProduct();
			if(prod.getId() == cartItem.getProductID() && cart.getId() == cartItem.getCartID())
			{
				stvar= itemrepo.findByProduct_idAndCart_id(cartItem.getProductID(), cartItem.getCartID());
				stvar.setQuantity(stvar.getQuantity() + cartItem.getQuantity());
				itemrepo.save(stvar);
				return stvar;
				
			}
			
		}
		
		Cart_item items = new Cart_item();
		
		items.setCart(cart);
		Product product = prodrepo.findById(cartItem.getProductID()).orElseThrow();
		items.setProduct(product);
		items.setQuantity(cartItem.getQuantity());
		
		itemrepo.save(items);
		
		return items;
		
		
		
	}
	
	@PutMapping("/users/{userId}/cart/items/{itemId}")
	public Cart_item azurirajKorpu(@PathVariable("userId") int userId,@PathVariable("itemId") int itemId,@RequestBody UpdateQuantity quantity)
	{
		
		Cart_item item = itemrepo.findByProduct_idAndCart_id(itemId, userId);
		item.setQuantity(quantity.getQuantity());
		itemrepo.save(item);
		
		return item;
		
		
	}
	
	@DeleteMapping("/users/{userId}/cart/items/{itemId}")
	public void obrisiKorpu(@PathVariable("userId") int userId,@PathVariable("itemId") int itemId,@RequestBody UpdateQuantity quantity)
	{
		
		Cart_item item = itemrepo.findByProduct_idAndCart_id(itemId, userId);
		itemrepo.deleteById(item.getId());;
		
		
		
		
		
	}
	
	
}
