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
@Table(name = "funds")
public class Fund {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private double amount;
	private String description;
	private Timestamp created;
	private int userId;
	
	public Fund() {}



	public Fund(int id, double amount, String description, Timestamp created, int user_id) {
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.created = created;
		this.userId = user_id;
	}

	


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
	
	
	
	

}
