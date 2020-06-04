package com.decagonhq.stocktradingapp.api.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int userId;
	private int transactionId; 
	private int options; //1 for fund, 2 for purchase and 3 for sell
	private Timestamp created;
	private String description;
	
	public Transaction() {}
	
	

	public Transaction(int userId, int transactionId, int options, Timestamp created, String description) {
		super();
		this.userId = userId;
		this.transactionId = transactionId;
		this.options = options;
		this.created = created;
		this.description = description;
	}
	
	
   


	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getOptions() {
		return options;
	}

	public void setOptions(int options) {
		this.options = options;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	
	
	
	

}
