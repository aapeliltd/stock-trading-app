package com.decagonhq.stocktradingapp.api.resource;

import java.util.List;

public class UserList {
	
	private int total;
	private List<UserResource> users;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<UserResource> getUsers() {
		return users;
	}
	public void setUsers(List<UserResource> users) {
		this.users = users;
	}
	
	

}
