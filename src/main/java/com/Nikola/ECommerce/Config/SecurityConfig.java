package com.Nikola.ECommerce.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Nikola.ECommerce.Security.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	
	
	private final CustomUserDetailsService userDetailsService;

	
	public SecurityConfig(CustomUserDetailsService userDetailsService)
	{
		this.userDetailsService = userDetailsService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
	{
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth->auth
					//users
					.requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/users/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/users/**").hasRole("ADMIN")

					//category
					.requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
					.requestMatchers(HttpMethod.POST,"/categories/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT,"/categories/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE,"/categories/**").hasRole("ADMIN")

					//cart
					.requestMatchers(HttpMethod.GET,"/users/{userId}/cart").hasRole("USER")
					.requestMatchers(HttpMethod.POST,"/users/{userId}/cart").hasRole("USER")
					.requestMatchers(HttpMethod.PUT,"/users/{userId}/cart/items/{itemId}").hasRole("USER")
					.requestMatchers(HttpMethod.DELETE,"/users/{userId}/cart").hasRole("USER")
					
					//orders
					.requestMatchers(HttpMethod.POST,"/users/{userId}/orders").hasRole("USER")
					.requestMatchers(HttpMethod.GET,"/orders/{userId}").hasRole("USER")
					.requestMatchers(HttpMethod.GET,"/orders").hasRole("ADMIN")

					//products
					.requestMatchers(HttpMethod.GET,"/products/**").permitAll()
					.requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
		            .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
		            
		            //auth
		            .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()
		            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
			.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults());
		
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration config) throws Exception {

	    return config.getAuthenticationManager();
	}
	
	
}
