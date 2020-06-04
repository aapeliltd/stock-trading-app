package com.decagonhq.stocktradingapp.api.resource;

import java.util.HashMap;

public class UserResource {
	
	private String email;
	private String username;
	private HashMap<String, String> href;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public HashMap<String, String> getHref() {
		return href;
	}
	public void setHref(HashMap<String, String> href) {
		this.href = href;
	}
	
	
	

}
