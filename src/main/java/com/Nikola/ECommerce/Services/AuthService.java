package com.Nikola.ECommerce.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Nikola.ECommerce.DTO.CreateUserRequest;
import com.Nikola.ECommerce.DTO.LoginRequest;
import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.model.User;

import jakarta.validation.Valid;

@Service
public class AuthService {
	
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	AuthenticationManager authenticationManager;



	AuthService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	

	public String register( CreateUserRequest userRequest) throws Exception{
		
		if(repo.findByEmail(userRequest.getEmail())!= null) {
			throw new Exception("email already exist");
		}
		
		User user = new User();
		
		PasswordEncoder enkoder = new BCryptPasswordEncoder();
		
		user.setEmail(userRequest.getEmail());
		user.setIme(userRequest.getIme());
		user.setPassword(enkoder.encode(userRequest.getPassword()));
		user.setPrezime(userRequest.getPrezime());
		user.setRole("USER");
		
		repo.save(user);
		
		return "uspesna registracija";
	}



	public String login(@Valid LoginRequest loginRequest) throws Exception{
		
		 authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(
			            loginRequest.getEmail(),
			            loginRequest.getPassword()
			        )
			    );

			    return "Login successful";


	}
	

}
