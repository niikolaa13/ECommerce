package com.Nikola.ECommerce.Controllers;

import java.util.List;

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

import com.Nikola.ECommerce.DTO.CreateUserRequest;
import com.Nikola.ECommerce.Services.UserService;
import com.Nikola.ECommerce.model.User;

import jakarta.validation.Valid;

@RestController
public class UserController {


	

	private final UserService service;

	public UserController(UserService service)
	{
		this.service = service;
	}

	
	
	@GetMapping("/users")
	public List<User> getUsers()
	{
		return service.getUsers();
	}
	
	@GetMapping("/users/{id}")
	public User getUsers(@PathVariable("id")int id)throws 	Exception
	{
		return service.getUsers(id);
	}
	
	@PostMapping("users")
	public ResponseEntity<User> addUser(@Valid @RequestBody CreateUserRequest userrequ)
	{
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(service.addUser(userrequ));
	}
	
	@PutMapping("users/{id}")
	public User updateUser(@PathVariable("id")int id, @RequestBody User user1 ) throws Exception
	{
		return service.updateUser(id, user1);
		
	}
	
	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable("id") int id) throws Exception
	{
		return service.deleteUser(id);
	}
	
}
