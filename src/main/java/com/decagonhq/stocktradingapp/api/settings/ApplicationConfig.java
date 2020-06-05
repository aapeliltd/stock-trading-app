package com.decagonhq.stocktradingapp.api.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.decagonhq.stocktradingapp.api.filter.JwtFilter;
import com.decagonhq.stocktradingapp.api.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
	
	//inject our custom user detail service here.
	@Autowired
	private CustomUserDetailsService customerUserDetailService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//we need to create our own custom user detail service.
		auth.userDetailsService(customerUserDetailService);
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		//create password encoder for the app. for simplicity, we are using no password encoder.
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//we are permitting all the request for login and register
		http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/api/v1/stocktradingapp/login", "/api/v1/stocktradingapp/register")
					.permitAll()
					.anyRequest()
					.authenticated()
					.and()
					.exceptionHandling()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
					
		
		//check filter before request
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		  web.ignoring().antMatchers("/v2/api-docs",
                  "/configuration/ui",
                  "/swagger-resources/**",
                  "/configuration/security",
                  "/swagger-ui.html",
                  "/webjars/**");
	}
	
	
	
	
	
	
	
	

}
