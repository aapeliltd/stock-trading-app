package com.decagonhq.stocktradingapp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decagonhq.stocktradingapp.api.model.Purchase;
import com.decagonhq.stocktradingapp.api.repository.PurchaseRepository;

@Service
public class PurchaseService {
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	public Purchase addNewPurchase(Purchase purchase) {
		return purchaseRepository.save(purchase);
	}
	
	public List<Purchase> getAllPurchasesByUserId(int userId) {
		return purchaseRepository.findAllByUserId(userId);
	}
	
	public Optional<Purchase> getPurchaseById(int id) {
		return purchaseRepository.findById(id);
	}
	
	public Purchase getPurchaseById(Optional<Integer> id) {
		return purchaseRepository.findById(id);
	}

}
