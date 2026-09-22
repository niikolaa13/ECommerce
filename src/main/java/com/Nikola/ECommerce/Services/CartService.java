package com.Nikola.ECommerce.Services;

import java.util.List;

import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Nikola.ECommerce.Repository.CartRepository;
import com.Nikola.ECommerce.Repository.Cart_itemRepository;
import com.Nikola.ECommerce.Repository.ProductRepository;
import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.Requests.CreateCart_ItemRequest;
import com.Nikola.ECommerce.Requests.UpdateQuantity;
import com.Nikola.ECommerce.model.Cart;
import com.Nikola.ECommerce.model.Cart_item;
import com.Nikola.ECommerce.model.Product;

@Service
public class CartService {

	
	@Autowired
	CartRepository repo;
	
	@Autowired 
	Cart_itemRepository itemrepo;
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	ProductRepository prodrepo;
	
	@Transactional
	public List<Cart_item> getCart(int id)
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
	
	
	public Cart_item addInCart( CreateCart_ItemRequest cartItem,int id)
	{
		
		Cart cart;
		if(repo.findByUser_id(id)==null) {
			cart = new Cart();
			cart.setUser(userrepo.findById(id).orElseThrow());
			repo.save(cart);
			repo.flush();
			
		}
		else {
			cart = repo.findByUser_id(id);
		}
		
		
		
		
		
		Cart_item itemss = itemrepo.findByProduct_idAndCart_id( cartItem.getProductID(), cartItem.getCartID());
		
		if(itemss!=null) {
			itemss.setQuantity(itemss.getQuantity()+cartItem.getQuantity());
			itemrepo.save(itemss);
			return itemss;
		}
		
		
			
		
		
		Cart_item items = new Cart_item();
		
		items.setCart(cart);
		Product product = prodrepo.findById(cartItem.getProductID()).orElseThrow();
		items.setProduct(product);
		items.setQuantity(cartItem.getQuantity());
		
		itemrepo.save(items);
		
		return items;
		
		
		
	}
	
	public Cart_item azurirajKorpu( int userId, int itemId, UpdateQuantity quantity)
	{
		
		Cart_item item = itemrepo.findByProduct_idAndCart_id(itemId, userId);
		if(item == null) {
			throw new ExecutionException("ne postoji ta stvar u korpi");
		}
		item.setQuantity(quantity.getQuantity());
		itemrepo.save(item);
		
		return item;
		
		
	}
	
	
	public void obrisiItemUKorpi(int userId,int itemId, UpdateQuantity quantity)
	{
		
		Cart_item item = itemrepo.findByProduct_idAndCart_id(itemId, userId);
		if(item == null) {
			throw new ExecutionException("ne postoji ta stvar u korpi");
		}
		if(item.getQuantity()-quantity.getQuantity()<=0) {
			itemrepo.deleteById(item.getId());
			return;
		}
		
		item.setQuantity(item.getQuantity()-quantity.getQuantity());
		itemrepo.save(item);
		
		
		
		
		
	}
	
}
