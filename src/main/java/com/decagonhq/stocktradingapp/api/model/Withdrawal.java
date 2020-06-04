package com.decagonhq.stocktradingapp.api.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "withdrawals")
public class Withdrawal {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private double amount;
	private String description;
	private Timestamp created;
	private int userId;
	private int purchaseId;
	
	public Withdrawal() {}
	
	public Withdrawal(double amount, String description, Timestamp created, int userId, int purchaseId) {
		super();
		this.amount = amount;
		this.description = description;
		this.created = created;
		this.userId = userId;
		this.purchaseId = purchaseId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	
	

}
