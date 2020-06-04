package com.decagonhq.stocktradingapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decagonhq.stocktradingapp.api.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

}
