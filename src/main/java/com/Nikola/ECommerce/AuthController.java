package com.Nikola.ECommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Nikola.ECommerce.DTO.CreateUserRequest;
import com.Nikola.ECommerce.DTO.LoginRequest;
import com.Nikola.ECommerce.Services.AuthService;

import jakarta.validation.Valid;


@RequestMapping("/auth")
@RestController
public class AuthController {

	@Autowired
	AuthService service;
	
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
