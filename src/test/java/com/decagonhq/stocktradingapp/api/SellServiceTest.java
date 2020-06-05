package com.decagonhq.stocktradingapp.api;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.decagonhq.stocktradingapp.api.model.Sell;
import com.decagonhq.stocktradingapp.api.repository.SellRepository;
import com.decagonhq.stocktradingapp.api.services.SellService;

@SpringBootTest
public class SellServiceTest {
	
	@Autowired
	private SellService sellService;
	
	@MockBean
	private SellRepository sellRepository;
	
	
	@Test
	public void addNewSaleTest() {
		Date date = new Date();
		Timestamp created = new Timestamp(date.getTime());
		Sell sell = new Sell(120.89, 199, created);
		when(sellRepository.save(sell)).thenReturn(sell);
		
		assertEquals(sell, sellService.addNewSale(sell), "Save new stock sale method failed");
		
	}
	
	@Test
	public void getSellByIdTest() {
		int id = 120;
		Optional<Sell> sell = sellRepository.findById(id);
		when(sellRepository.findById(id)).thenReturn(sell);
		
		assertEquals(sell, sellService.getSellById(id));
	}

}
