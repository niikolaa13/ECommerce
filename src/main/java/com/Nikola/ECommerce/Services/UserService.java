package com.Nikola.ECommerce.Services;

import java.util.List;

import com.Nikola.ECommerce.Exceptions.ResourceNotFoundException;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Nikola.ECommerce.DTO.CreateUserRequest;
import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.model.User;

@Service
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository repo;



	public UserService(PasswordEncoder passwordEncoder,UserRepository repo) {
		this.passwordEncoder = passwordEncoder;
		this.repo = repo;
	}
	
	
	
	public List<User> getUsers()
	{
		return repo.findAll();
	}
	
	
	public User getUsers(int id)throws  Exception
	{
		return repo.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji user sa ID:"+id));
	}
	
	
	public User addUser( CreateUserRequest userrequ)
	{
		
			
	
		
		String lozinka = passwordEncoder.encode(userrequ.getPassword());
		User user = new User();
		user.setIme(userrequ.getIme());
		user.setPrezime(userrequ.getPrezime());
		user.setEmail(userrequ.getEmail());
		user.setPassword(lozinka);
		user.setRole(userrequ.getRole());
		
		repo.save(user);
		
		return user;
	}
	
	
	public User updateUser(int id,  User user1 ) throws Exception
	{
		User user = repo.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji user sa ID:"+id));
		
		if(user1.getIme() != null){
		user.setIme(user1.getIme());
		}
		if(user1.getPrezime() != null){
		user.setPrezime(user1.getPrezime());
		}
		if(user1.getEmail() != null){
		user.setEmail(user1.getEmail());
		}
		if(user1.getPassword() != null){
		user.setPassword(user1.getPassword());
		}
		if(user1.getRole() != null){
		user.setRole(user1.getRole());
		}
		
		repo.save(user);
		
		return user;
		
	}
	
	
	public User deleteUser( int id)throws  Exception
	{
		User user = repo.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Ne postoji user sa ID:"+id));
		
		repo.deleteById(id);
		
		return user;
	}
}
