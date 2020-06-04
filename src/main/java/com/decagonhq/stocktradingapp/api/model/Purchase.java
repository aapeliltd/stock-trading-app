package com.decagonhq.stocktradingapp.api.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchases")
public class Purchase {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int size;
	private double price;
	private Timestamp created;
	private int userId;
	private String companyName;
	private String companySymbol;
	
	public Purchase() {}

	public Purchase(int size, double price, Timestamp created, int user_id, String companyName, String companySymbol) {
		super();
		this.size = size;
		this.price = price;
		this.created = created;
		this.userId = user_id;
		this.companyName = companyName;
		this.companySymbol = companySymbol;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public int getUser_id() {
		return userId;
	}

	public void setUser_id(int user_id) {
		this.userId = user_id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanySymbol() {
		return companySymbol;
	}

	public void setCompanySymbol(String companySymbol) {
		this.companySymbol = companySymbol;
	}

	
	
	

}
