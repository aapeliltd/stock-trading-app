package com.decagonhq.stocktradingapp.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.decagonhq.stocktradingapp.api.model.Purchase;
import com.decagonhq.stocktradingapp.api.repository.PurchaseRepository;
import com.decagonhq.stocktradingapp.api.services.PurchaseService;

@SpringBootTest
public class PurchaseServiceTest {

	@Autowired
	private PurchaseService purchaseService;
	
	@MockBean
	private PurchaseRepository purchaseRepository;
	
	@Test
	public void addNewPurchase() {
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		Purchase purchase = new Purchase(100, 120, created, 1, "APLE INC", "APPL");
		when(purchaseRepository.save(purchase)).thenReturn(purchase);
		
		assertEquals(purchase, purchaseService.addNewPurchase(purchase), "Method to purchase new stock failed.");
	}
	
	@Test
	public void getAllPurchaseByUserId() {
		int userId = 99;
		Date date = new Date();
		Timestamp created =new Timestamp(date.getTime());
		when(purchaseRepository.findAllByUserId(userId)).thenReturn(Stream.of(
				new Purchase(100, 1300, created, userId, "Neflix", "NFLX"),
				new Purchase(123, 1300, created, userId, "Neflix", "NFLX")
				).collect(Collectors.toList()));
		assertEquals(2, purchaseService.getAllPurchasesByUserId(userId).size(), "get all purchase by userId failed");
		
	}
	@Test
	public void getPurchaseById() {
		int id = 99;
		Optional<Purchase> purchase = purchaseRepository.findById(id);
		when(purchaseRepository.findById(id)).thenReturn(purchase);
		
		assertEquals(purchase, purchaseService.getPurchaseById(id), "find purchase by Id failed");
		
	}
	
	
	
	
}
