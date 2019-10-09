package com.example.stock.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.Stock;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.service.ConfigService;
import com.example.stock.service.StockService;
import com.example.stock.util.DateUtil;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = { "Content-Type", "Accept",
		"x-xsrf-token", "Access-Control-Allow-Headers", "Origin", "Access-Control-Request-Method",
		"Access-Control-Request-Headers" })
@RestController
@RequestMapping("/stock")
public class StockController {
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	ConfigService configService; 
	
	@GetMapping("/get-all-Stocks")
	public List<Stock> getStockList() {
		
		return stockService.getAllStocks();
	}
	
	@GetMapping("/load-stock")
	public void loadStocks(@PathParam("dir") String sourceDir) {
		stockService.saveStocks(sourceDir);
	}
	
	@PostMapping(value="/search",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Stock> getStocks(@PathParam("startDate") String startDate,@PathParam("endDate") String endDate,@RequestBody List<Filter> filter) {
		try {
			return stockService.findHoldingStocks(DateUtil.stringToDate(startDate), DateUtil.stringToDate(endDate),filter);
		} catch (Exception e) {
			System.out.println(e);
		}
		return Arrays.asList();
	}
	
	@GetMapping("/get-filter")
	public Object getFilter(@PathParam("type") String type) throws IOException {
		HashMap<String, Object> res = configService.getFile("Data.json");
		return res.get(type);
	}
	
	@GetMapping("/symbol")
	public Object getSymbolNames() throws IOException {
		List<HashMap<String, Object>> symbols = configService.getFileAsList("stock-list.json");
		return symbols;
	}
}
