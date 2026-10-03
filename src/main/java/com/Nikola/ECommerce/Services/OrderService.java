package com.Nikola.ECommerce.Services;

import java.util.Date;
import java.util.List;

import com.Nikola.ECommerce.Exceptions.ResourceNotFoundException;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.Nikola.ECommerce.Repository.CartRepository;
import com.Nikola.ECommerce.Repository.Cart_itemRepository;
import com.Nikola.ECommerce.Repository.Order_itemRepository;
import com.Nikola.ECommerce.Repository.OrdersRepository;
import com.Nikola.ECommerce.Repository.ProductRepository;
import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.model.Cart;
import com.Nikola.ECommerce.model.Cart_item;
import com.Nikola.ECommerce.model.Order_item;
import com.Nikola.ECommerce.model.Orders;
import com.Nikola.ECommerce.model.User;

@Service
public class OrderService {

	
	
	private final OrdersRepository repo;
	private final Order_itemRepository orderItemRepo;
	private final UserRepository userRepo;
	private final CartRepository cartRepo;
	private final Cart_itemRepository cartItemRepo;
	private final ProductRepository productRepo;
	
	public OrderService(OrdersRepository repo,Order_itemRepository orderItemRepo
			,UserRepository userRepo,CartRepository cartRepo,Cart_itemRepository cartItemRepo,ProductRepository productRepo)
	{
		this.repo=repo;
		this.orderItemRepo=orderItemRepo;
		this.userRepo = userRepo;
		this.cartRepo = cartRepo;
		this.cartItemRepo = cartItemRepo;
		this.productRepo = productRepo;
	}
	
	
	@Transactional
	public Orders addOrder(int userId) throws Exception
	{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		if(userRepo.findByEmail(email).getId()!= userId) {
			throw new ResponseStatusException(
			        HttpStatus.FORBIDDEN,
			        "You are not allowed to access to other users carts");
		}

	    User user = userRepo.findById(userId).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji User sa ID:"+userId));

	    Cart cart = cartRepo.findByUser_id(userId);

	    if (cart == null) {
	        throw new ResourceNotFoundException("Korpa je prazna");
	    }

	    List<Cart_item> cart_items =
	            cartItemRepo.findAllByCart_id(cart.getId());

	    if (cart_items.isEmpty()) {
	        throw new RuntimeException("Korpa je prazna");
	    }

	    // 1. Provera stock-a
	    for (Cart_item n : cart_items) {

	        if (n.getProduct().getStock() < n.getQuantity()) {
	            throw new RuntimeException(
	                    "Nema dovoljno proizvoda na stanju: "
	                    + n.getProduct().getName()
	            );
	        }
	    }

	    // 2. Izračunavanje ukupne cene
	    float price = 0;

	    for (Cart_item n : cart_items) {

	        float pom = n.getProduct().getPrice();
	        int kolicina = n.getQuantity();

	        price = price + (pom * kolicina);
	    }

	    // 3. Pravljenje Order-a
	    Orders order = new Orders();

	    order.setUser(user);
	    order.setTotal_price(price);
	    order.setCreated_at(new Date());
	    order.setStatus("u obradi");

	    repo.save(order);
	

	    // 4. Pravljenje OrderItem-a i smanjivanje stock-a
	    for (Cart_item n : cart_items) {

	        Order_item orderItem = new Order_item();

	        orderItem.setOrder(order);
	        orderItem.setProduct(n.getProduct());
	        orderItem.setQuantity(n.getQuantity());
	        orderItem.setPrice(n.getProduct().getPrice());

	        orderItemRepo.save(orderItem);

	        n.getProduct().setStock(
	                n.getProduct().getStock() - n.getQuantity()
	        );

	        productRepo.save(n.getProduct());
	    }

	    // 5. Praznimo korpu
	    cartItemRepo.deleteAllByCart_id(cart.getId());

	    return order;
	}
	
	
	public List<Orders> getAllOrders() {
		

		
	    return repo.findAll();
	}
	
	
	public List<Orders> getOrdersByUser(@PathVariable("userId")int userId)
	{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		if(userRepo.findByEmail(email).getId()!= userId) {
			throw new ResponseStatusException(
					HttpStatus.FORBIDDEN,
					"You are not allowed to access to other users carts"
					);
		}
		
		
		return repo.findAllByUser_id(userId);
		
	}
	
}
