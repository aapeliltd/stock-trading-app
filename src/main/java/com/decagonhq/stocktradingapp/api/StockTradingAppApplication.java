package com.decagonhq.stocktradingapp.api;

import java.util.Collection;
import java.util.Collections;
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

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
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
	
	@Bean
	public Docket swaggerConfiguration() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				//.paths(PathSelectors.ant("/api/*"))
				.apis(RequestHandlerSelectors.basePackage("com.decagonhq"))
				.build()
				.apiInfo(AppDocInfo());
				
	}
	
	@SuppressWarnings("deprecation")
	private ApiInfo AppDocInfo() {
		
		return new ApiInfo(
				"Stock Trading App", 
				"Application is for a stock trading app, users can fund their"
				+ " accounts, buy, and sell stocks as well as query transanction history",
				"1.0", 
				"https://decagonhq.com", 
				"Decagon", 
				"Api License- MIT", 
				"https://decagonhq.com");
	}
	
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(StockTradingAppApplication.class, args);
	}

}
