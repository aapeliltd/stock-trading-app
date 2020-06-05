package com.decagonhq.stocktradingapp.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;
import com.decagonhq.stocktradingapp.api.services.UserService;


@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void getAllUsersTest() {
		when(userRepository.findAll()).thenReturn(Stream.of(
				new User(101,"test1@gmail.com","test", "test"),
				new User(102,"test2@gmail.com","test2", "test2")
		).collect(Collectors.toList()));
		
		assertEquals(2, userService.getAllUsers().size());
	}
	@Test
	public void addNewUserTest() {
		User user = new User(999, "newuser@gmail.com", "newuser", "pass");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.addNewUser(user));
	}
	
	@Test
	public void getUserById() {
		int id = 999;
		Optional<User> user = userRepository.findById(id);
		when(userRepository.findById(id)).thenReturn(user);
		assertEquals(user, userService.getUserById(id));
	}
	
	
}
