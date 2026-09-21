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

import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.Requests.CreateUserRequest;
import com.Nikola.ECommerce.model.User;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	UserRepository repo;
	
	
	@GetMapping("/users")
	public List<User> getUsers()
	{
		return repo.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUsers(@PathVariable("id")int id)
	{
		return repo.findById(id).orElseThrow();
	}
	
	@PostMapping("users")
	public User addUser(@Valid @RequestBody CreateUserRequest userrequ)
	{
		User user = new User();
		user.setIme(userrequ.getIme());
		user.setPrezime(userrequ.getPrezime());
		user.setEmail(userrequ.getEmail());
		user.setPassword(userrequ.getPassword());
		user.setRole(userrequ.getRole());
		
		repo.save(user);
		
		return user;
	}
	
	@PutMapping("users/{id}")
	public User updateUser(@PathVariable("id")int id, @RequestBody User user1 )
	{
		User user = repo.findById(id).orElseThrow();
		
		if(user1.getIme() != null)
		user.setIme(user1.getIme());
		if(user1.getPrezime() != null)
		user.setPrezime(user1.getPrezime());
		if(user1.getEmail() != null)
		user.setEmail(user1.getEmail());
		if(user1.getPassword() != null)
		user.setPassword(user1.getPassword());
		if(user1.getRole() != null)
		user.setRole(user1.getRole());
		
		repo.save(user);
		
		return user;
		
	}
	
	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable("id") int id)
	{
		User user = repo.findById(id).orElseThrow();
		
		repo.deleteById(id);
		
		return user;
	}
	
}
