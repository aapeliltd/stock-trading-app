package com.decagonhq.stocktradingapp.api;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.decagonhq.stocktradingapp.api.model.Transaction;
import com.decagonhq.stocktradingapp.api.repository.TransactionRepository;
import com.decagonhq.stocktradingapp.api.services.TransactionService;

@SpringBootTest
public class TransactionServiceTest {
	
	@Autowired
	private TransactionService transactionService;
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	
	@Test
	public void addNewTransactionTest() {
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		Transaction trans = new Transaction(12, 12, 2, created, "this is a new transaction from test");
		when(transactionRepository.save(trans)).thenReturn(trans);
		
		assertEquals(trans, transactionService.addNewTransaction(trans));
		
	}
	
	@Test
	public void getAllTransactionsByUserIdTest() {
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		int id = 99;
		when(transactionRepository.findAllByUserId(id)).thenReturn(Stream.of(
				new Transaction(1, 2, 2, created, "new one"), 
				new Transaction(2, 2, 2, created, "new one")
				).collect(Collectors.toList()));
		
		assertEquals(2, transactionService.getAllTransactionsByUserId(id).size());
	}

}
