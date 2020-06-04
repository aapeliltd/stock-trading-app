package com.decagonhq.stocktradingapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.decagonhq.stocktradingapp.api.model.Sell;

public interface SellRepository extends JpaRepository<Sell, Integer> {

}
