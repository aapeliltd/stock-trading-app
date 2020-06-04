package com.decagonhq.stocktradingapp.api.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sells")
public class Sell {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private double price;
	private int purchaseId;
	private Timestamp created;
	
	public Sell() {}

	public Sell(double price, int purchaseId, Timestamp created) {
		super();
		this.price = price;
		this.purchaseId = purchaseId;
		this.created = created;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}



	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Timestamp getCreated() {
		return created;
	}
	
	
	
	
	

}
