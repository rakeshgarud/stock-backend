package com.example.stock.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stock.bean.ExchangeActivity;
import com.example.stock.bean.Stock;
import com.example.stock.dto.Filter;

@Service
public interface StockService {

	public List<Stock> getAllStocks();
	
	public void saveStocks(String sourceDir);
	
	public List<Stock> getStocks(Date startDate,Date endDate);
	
	public List<Stock> findHoldingStocks(Date startDate,Date endDate,List<Filter> filters);
	
	public void loadActivityData() throws Exception;

	public List<ExchangeActivity> getActivityData(Date startDate, Date endDate) throws Exception;
	
}
