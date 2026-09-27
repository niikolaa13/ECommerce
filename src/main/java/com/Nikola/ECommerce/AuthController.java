package com.Nikola.ECommerce;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nikola.ECommerce.DTO.CreateUserRequest;
import com.Nikola.ECommerce.DTO.LoginRequest;
import com.Nikola.ECommerce.Services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	private final AuthService service;
	
	public AuthController(AuthService service)
	{
		this.service = service;
	}
	
	@PostMapping("/register")
	public String register(@Valid @RequestBody CreateUserRequest userRequest) throws Exception
	{
		
		return service.register(userRequest);
		
	
		
	}
	
	@PostMapping("/login")
	public String login(@Valid @RequestBody LoginRequest loginRequest) throws Exception
	{
		return service.login(loginRequest);
	}
	
	
	
}
