package com.example.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.stock.bean.StockOptionsEquity;
import com.example.stock.constants.Constant;
import com.example.stock.repo.StockOptionsEquityRepository;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StockOptionsEquityLookupService {
	
	@Autowired
	private StockOptionsEquityRepository stockOptionEquityRepository;
	
	@Autowired
	private ConfigService configService;
	
	private final static Logger logger = LoggerFactory.getLogger(StockOptionsEquityLookupService.class);
	
	@Async("threadPoolTaskExecutor")
    public void loadStocksOptionsData() throws InterruptedException {
		
       List<HashMap<String, Object>> symbols = configService.getFileAsList("stock-list.json");
       	for(HashMap<String, Object> symbol : symbols) {
       		String url = Constant.EQUITY_CHART_URL.split("=")[0]+"="+symbol.get("symbol");
    		List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(url);
    		logger.info("Fetching data for : "+symbol.get("symbol")+" URL ="+url);
    		List<StockOptionsEquity> equities = new ArrayList<StockOptionsEquity>();
    		resultObj.forEach(s -> {
    			ObjectMapper mapper = new ObjectMapper();
    			StockOptionsEquity equity = new StockOptionsEquity();
    			equity = mapper.convertValue(s, StockOptionsEquity.class);
    			equity.setSymbol(symbol.get("symbol").toString());
    			equity.setDate(DateUtil.getCurretDate());
    			equity.setId(null);
    			equity.setPostionsVol(equity.getChnginOI() / equity.getVolume());
    			equities.add(equity);
    		});
    		String sourceDir = (String) configService.getConfigByName(Constant.STOCK_OPTIONS_SOURCE_DIR);
    		FileUtil.saveStockOptionsEquityAsJsonFile(equities,sourceDir);
    		stockOptionEquityRepository.saveAll(equities);
    		logger.info("Record successfully saved for : "+symbol.get("symbol")+" URL ="+url);
       	}
    }
	
}
