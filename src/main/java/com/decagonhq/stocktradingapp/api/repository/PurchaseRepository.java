package com.decagonhq.stocktradingapp.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decagonhq.stocktradingapp.api.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	
	
	List<Purchase> findAllByUserId(int id);
	
	Purchase findById(Optional<Integer> id);
	
}

