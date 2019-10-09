package com.example.stock.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock.bean.ExchangeActivity;
import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.Stock;
import com.example.stock.bean.StockOptionsEquity;
import com.example.stock.constants.Constant;
import com.example.stock.repo.ExchangeActivityRepository;
import com.example.stock.repo.NiftyEquityDerivativeRepository;
import com.example.stock.repo.StockOptionsEquityRepository;
import com.example.stock.repo.StockRepository;
import com.example.stock.util.CSVReader;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DBDataLoaderServiceImpl implements DBDataLoaderService {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private ConfigService configService;
	
	private final static Logger logger = LoggerFactory.getLogger(StockOptionsEquityLookupService.class);

	@Autowired
	private NiftyEquityDerivativeRepository equityDerivativeRepository;
	
	@Autowired
	private StockOptionsEquityRepository stockOptionsequityRepository;
	
	@Autowired
	private ExchangeActivityRepository activityRepository;

	//Load equity nifty,stock option data from file to DB
	@Override
	public void loadEquityOptionsToDB() {
		logger.info("Loading equity data to DB");
		String sourceDir = (String) configService.getConfigByName(Constant.NIFTY_SOURCE_DIR);
		loadEquityFromFile(sourceDir, ".json");
		sourceDir = (String) configService.getConfigByName(Constant.STOCK_OPTIONS_SOURCE_DIR);
		loadStocksOptionsEquityFromFile(sourceDir, ".json");
	}

	//Load stocks data from file to DB
	@Override
	public void loadStockDataToDB() {
		logger.info("Loading stocks data to DB");
		String sourceDir =  (String) configService.getConfigByName(Constant.STOCK_SOURCE_DIR);
		loadStockFromFile(sourceDir, ".csv");
	}
	
	private List<NiftyEquityDerivative> loadEquityFromFile(String sourceDir, String fileFormat) {
		String DIR = null;
		if (sourceDir != null) {
			DIR = sourceDir;
		}
		List<NiftyEquityDerivative> equities = new ArrayList<NiftyEquityDerivative>();
		File[] files = FileUtil.getAllFiles(DIR, fileFormat);
		if (files == null) {
			return Arrays.asList();
		}
		for (int i = 0; i < files.length; i++) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			try {
				equities = Arrays.asList(mapper.readValue(files[i], NiftyEquityDerivative[].class));
				equityDerivativeRepository.saveAll(equities);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return equities;
	}
	

	@Override
	public void loadFIIDataToDB() {
		logger.info("Loading activity data to DB");
		String sourceDir =  (String) configService.getConfigByName(Constant.FII_SOURCE_DIR);
		loadActivityFromFile(sourceDir, ".csv");
	}
	
	private List<StockOptionsEquity> loadStocksOptionsEquityFromFile(String sourceDir, String fileFormat) {
		List<StockOptionsEquity> equities = new ArrayList<StockOptionsEquity>();
		File[] files = FileUtil.getAllFiles(sourceDir, fileFormat);
		if (files == null) {
			return Arrays.asList();
		}
		for (int i = 0; i < files.length; i++) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			try {
				equities = Arrays.asList(mapper.readValue(files[i], StockOptionsEquity[].class));
				stockOptionsequityRepository.saveAll(equities);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return equities;
	}

	private void loadStockFromFile(String sourceDir, String fileFormat) {
		String DIR = null;
		if (sourceDir != null) {
			DIR = sourceDir;
		}else
			DIR =  (String) configService.getConfigByName(Constant.STOCK_SOURCE_DIR);
		File[] files = FileUtil.getAllFiles(DIR, fileFormat);
		for (int i = 0; i < files.length; i++) {
			List<Stock> stocks = CSVReader.writeCsvToStock(files[i].getAbsolutePath());
			stockRepository.saveAll(stocks);
		}
	}

	private void loadActivityFromFile(String sourceDir, String fileFormat) {
		String DIR = null;
		if (sourceDir != null) {
			DIR = sourceDir;
		}else
			DIR =  (String) configService.getConfigByName(Constant.FII_SOURCE_DIR);
		File[] files = FileUtil.getAllFiles(DIR, fileFormat);
		for (int i = 0; i < files.length; i++) {
			List<ExchangeActivity> exchangeActivity = CSVReader.writeCsvToActivity(files[i].getAbsolutePath());
			activityRepository.saveAll(exchangeActivity);
		}
	}
}
