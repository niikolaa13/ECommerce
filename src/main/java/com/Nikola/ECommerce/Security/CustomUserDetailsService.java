package com.Nikola.ECommerce.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Nikola.ECommerce.Repository.UserRepository;
import com.Nikola.ECommerce.model.User;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	private final UserRepository userRepo;
	
	public CustomUserDetailsService(UserRepository userRepository)
	{
		this.userRepo = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(username);
		
		if(user == null)
		{
			throw new UsernameNotFoundException("korisnik nije pronadjen");
		}
		
		return new CustomUserDetails(user);
		
	}

}
