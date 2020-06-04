package com.decagonhq.stocktradingapp.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int userId;
	private int purchaseId;
	private int sellId;
	
	public Transaction() {}
	
	public Transaction(int userId, int purchaseId, int sellId) {
		super();
		this.userId = userId;
		this.purchaseId = purchaseId;
		this.sellId = sellId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getSellId() {
		return sellId;
	}
	public void setSellId(int sellId) {
		this.sellId = sellId;
	}
	
	
	
	

}
