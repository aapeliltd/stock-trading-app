package com.decagonhq.stocktradingapp.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.Transaction;
import com.decagonhq.stocktradingapp.api.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	public Transaction addNewTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public List<Transaction> getAllTransactionsByUserId(int userId) {
		return transactionRepository.findAllByUserId(userId);
	}
}
