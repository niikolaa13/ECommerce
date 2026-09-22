package com.Nikola.ECommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.Requests.CreateUserRequest;
import com.Nikola.ECommerce.model.User;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	
	
	public List<User> getUsers()
	{
		return repo.findAll();
	}
	
	
	public User getUsers(int id)
	{
		return repo.findById(id).orElseThrow();
	}
	
	
	public User addUser( CreateUserRequest userrequ)
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
	
	
	public User updateUser(int id,  User user1 )
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
	
	
	public User deleteUser( int id)
	{
		User user = repo.findById(id).orElseThrow();
		
		repo.deleteById(id);
		
		return user;
	}
}
