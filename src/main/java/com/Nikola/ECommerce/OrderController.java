package com.Nikola.ECommerce;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
public class OrderController {

	@Autowired
	OrdersRepository repo;
	
	@Autowired
	Order_itemRepository orderItemRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	Cart_itemRepository cartItemRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@PostMapping("/users/{userId}/orders")
	@Transactional
	public Orders addOrder(@PathVariable("userId") int userId) {

	    User user = userRepo.findById(userId).orElseThrow();

	    Cart cart = cartRepo.findByUser_id(userId);

	    if (cart == null) {
	        throw new RuntimeException("Korisnik nema korpu");
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
	
	@GetMapping("/orders")
	public List<Orders> getAllOrders() {
	    return repo.findAll();
	}
	
	@GetMapping("/orders/{userId}")
	public List<Orders> getOrdersByUser(@PathVariable("userId")int userId)
	{
		
		return repo.findAllByUser_id(userId);
		
	}
	
}
