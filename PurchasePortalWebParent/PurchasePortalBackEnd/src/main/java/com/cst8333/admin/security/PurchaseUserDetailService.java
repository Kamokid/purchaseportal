package com.cst8333.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cst8333.admin.user.UserRepository;
import com.cst8333.common.entity.User;

public class PurchaseUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if(user != null) {
			return new PurchaseUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email: " +email);
	}

}
