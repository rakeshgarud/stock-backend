package com.example.stock.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.stock.bean.ExchangeActivity;

@Repository
public interface ExchangeActivityRepository extends CrudRepository<ExchangeActivity, Long>{

}
