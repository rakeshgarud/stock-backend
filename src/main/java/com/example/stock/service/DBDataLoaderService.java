package com.example.stock.service;

import org.springframework.stereotype.Service;

@Service
public interface DBDataLoaderService {
	
	public void loadEquityOptionsToDB();
	
	public void loadStockDataToDB();
	
	public void loadFIIDataToDB();
	
}
