package com.example.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.StockOptionsEquity;
import com.example.stock.dto.SearchFilter;

@Service
public interface EquityService {

	public List<NiftyEquityDerivative> serachNiftyEquity(SearchFilter search);

	public List<NiftyEquityDerivative> getNiftyPremiumDK(SearchFilter search);

	public void saveNiftyEquityDerivatives();

	public void saveStockOptionsEquity();
	
	public List<StockOptionsEquity> serachStocksOptionEquity(SearchFilter search);

	//public List<StockOptionsEquity> getStockOptionPremiumDK(SearchFilter search);

	

}
