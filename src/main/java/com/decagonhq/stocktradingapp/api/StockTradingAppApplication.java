package com.decagonhq.stocktradingapp.api;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.decagonhq.stocktradingapp.api.model.User;
import com.decagonhq.stocktradingapp.api.repository.UserRepository;

@SpringBootApplication
public class StockTradingAppApplication {
	
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		
		return WebClient.builder()
				.baseUrl("https://cloud-sse.iexapis.com/stable/stock")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}
	
	
	//inject user repository here.
	@Autowired
	private UserRepository userRepository;
	
	//create two users for testing when you run the application.
	@PostConstruct //This will make this method to load at start-up of application.
	public void initUsers() {
		
		List<User> users = Stream.of(
				  
				new User(1, "user01@gmail.com", "user01", "password"),
				new User(2, "user02@gmail.com", "user02", "password")
				
				).collect(Collectors.toList());
		
		//save the list of users to H2 database.
		userRepository.saveAll(users);
		
	}
	
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(StockTradingAppApplication.class, args);
	}

}
