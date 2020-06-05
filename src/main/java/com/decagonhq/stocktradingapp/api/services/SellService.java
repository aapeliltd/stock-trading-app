package com.decagonhq.stocktradingapp.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.Sell;
import com.decagonhq.stocktradingapp.api.repository.SellRepository;

@Service
public class SellService {

	@Autowired
	private SellRepository sellRepository;
	
	public Sell addNewSale(Sell sell) {
		return sellRepository.save(sell);
	}
	
	public Optional<Sell> getSellById(int id) {
		return sellRepository.findById(id);
	}
	
}
