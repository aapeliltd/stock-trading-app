package com.decagonhq.stocktradingapp.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.decagonhq.stocktradingapp.api.services.CustomUserDetailsService;
import com.decagonhq.stocktradingapp.api.utility.JsonWebUtility;

// by extending to Once per request filter... it will filter every request once.
@Component // because it will be auto detected once the application load.
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JsonWebUtility jsonWebUtility;
	
	@Autowired
	private CustomUserDetailsService customeUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		//get me the token by key (authorization as the key)
		String authorizationHeader = request.getHeader("Authorization");
		
		String token = null;
		String userName = null;
		
		//check if is not null and start with Bearer
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			
			token = authorizationHeader.substring(7); //count from Bearer to the space after.
			
			userName = jsonWebUtility.extractUsername(token);
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = customeUserDetailService.loadUserByUsername(userName);
			
			//now that we have token and username, lets validate it.
			if(jsonWebUtility.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
		}
	
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("decagonhq", "no-cache");
        filterChain.doFilter(request, response);
		
	}
	
	

}
