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
	private double purchaseId;
	private Timestamp created;
	
	public Sell() {}

	public Sell(double price, double purchase_id, Timestamp created) {
		super();
		this.price = price;
		this.purchaseId = purchase_id;
		this.created = created;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPurchase_id() {
		return purchaseId;
	}

	public void setPurchase_id(double purchase_id) {
		this.purchaseId = purchase_id;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	
	

}
