package com.decagonhq.stocktradingapp.api.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;

//our customer user detail service will implement UserDetailService class.
@Service // so that spring will treat this class as one.
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		//get user from database by username.
		User user = userRepository.findByUserName(userName);
		// push user object to spring security to validate with username and password.
		return new org.springframework.security.core.userdetails.User(user.getUserName(), 
				user.getPassword(), new ArrayList<>());
	}

}
