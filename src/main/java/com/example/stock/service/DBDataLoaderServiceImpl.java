package com.example.stock.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock.bean.ExchangeActivity;
import com.example.stock.bean.IntraDayNifty;
import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.Stock;
import com.example.stock.bean.StockOptionsEquity;
import com.example.stock.constants.Constant;
import com.example.stock.enums.Column;
import com.example.stock.repo.ExchangeActivityRepository;
import com.example.stock.repo.IntraDayNiftyEquityRepository;
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
	
	private final static Logger logger = LoggerFactory.getLogger(StockOptionsEquityService.class);

	@Autowired
	private NiftyEquityDerivativeRepository equityDerivativeRepository;
	
	@Autowired
	private IntraDayNiftyEquityRepository intraDayNiftyEquityRepository;
	
	@Autowired
	private StockOptionsEquityRepository stockOptionsequityRepository;
	
	@Autowired
	private ExchangeActivityRepository activityRepository;

	//Load equity nifty,stock option data from file to DB
	@Override
	public void loadEquityOptionsToDB() {
		logger.info("Loading Nifty and Stock Options data to DB");
		String sourceDir = (String) configService.getConfigByName(Constant.NIFTY_SOURCE_DIR);
		loadEquityFromFile(sourceDir, ".json");
		
		sourceDir = (String) configService.getConfigByName(Constant.INTRADAY_NIFTY_SOURCE_DIR);
		//loadNiftyIntradayFromFile(sourceDir, ".json");
		
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
		double postionsVol =0.0;
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
				//equities = Arrays.asList(mapper.readValue(files[i], NiftyEquityDerivative[].class));
				equities = Arrays.asList(mapper.readValue(files[i], NiftyEquityDerivative[].class));
				
				/*for (int j =0; j < equities.size(); j++) {
					postionsVol = equities.get(j).getChnginOI() / equities.get(j).getVolume();
					equities.get(j).setPostionsVol(postionsVol);
					//System.out.println(equities.get(j).getPostionsVol());
				}*/
				List<NiftyEquityDerivative> call = equities.stream().filter(eq->eq.getType()==Column.CALL.getColumn()).collect(Collectors.toList());
				List<NiftyEquityDerivative> callDB = (List<NiftyEquityDerivative>) equityDerivativeRepository.saveAll(call);
				
				List<NiftyEquityDerivative> put = equities.stream().filter(eq->eq.getType()==Column.PUT.getColumn()).collect(Collectors.toList());
				put.stream().forEach(putEq->{
					Optional<NiftyEquityDerivative> eqty = call.stream()
							.filter(pre -> pre.getRowNo() == putEq.getRowNo()).findFirst();
					if (eqty.isPresent()) {
						putEq.setPutId(eqty.get().getId());
					}
				});
				equityDerivativeRepository.saveAll(put);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return equities;
	}
	

	private List<IntraDayNifty> loadNiftyIntradayFromFile(String sourceDir, String fileFormat) {
		String DIR = null;
		double postionsVol =0.0;
		if (sourceDir != null) {
			DIR = sourceDir;
		}
		List<IntraDayNifty> equities = new ArrayList<IntraDayNifty>();
		File[] files = FileUtil.getAllFiles(DIR, fileFormat);
		if (files == null) {
			return Arrays.asList();
		}
		for (int i = 0; i < files.length; i++) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			try {
				//equities = Arrays.asList(mapper.readValue(files[i], NiftyEquityDerivative[].class));
				equities = Arrays.asList(mapper.readValue(files[i], IntraDayNifty[].class));
				
				/*for (int j =0; j < equities.size(); j++) {
					postionsVol = equities.get(j).getChnginOI() / equities.get(j).getVolume();
					equities.get(j).setPostionsVol(postionsVol);
					//System.out.println(equities.get(j).getPostionsVol());
				}
				intraDayNiftyEquityRepository.saveAll(equities);
				*/
				List<IntraDayNifty> call = equities.stream().filter(eq->eq.getType()==Column.CALL.getColumn()).collect(Collectors.toList());
				List<IntraDayNifty> callDB = (List<IntraDayNifty>) intraDayNiftyEquityRepository.saveAll(call);
				
				List<IntraDayNifty> put = equities.stream().filter(eq->eq.getType()==Column.PUT.getColumn()).collect(Collectors.toList());
				put.stream().forEach(putEq->{
					Optional<IntraDayNifty> eqty = call.stream()
							.filter(pre -> pre.getRowNo() == putEq.getRowNo()).findFirst();
					if (eqty.isPresent()) {
						putEq.setPutId(eqty.get().getId());
					}
				});
				intraDayNiftyEquityRepository.saveAll(put);
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
		double postionsVol =0.0;
		if (files == null) {
			return Arrays.asList();
		}
		for (int i = 0; i < files.length; i++) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			try {
				equities = Arrays.asList(mapper.readValue(files[i], StockOptionsEquity[].class));
			/*	for (int j =0; j < equities.size(); j++) {
					postionsVol = equities.get(j).getChnginOI() / equities.get(j).getVolume();
					equities.get(j).setPostionsVol(postionsVol);
					//System.out.println(equities.get(j).getPostionsVol());
				}
				stockOptionsequityRepository.saveAll(equities);
				*/
				List<StockOptionsEquity> call = equities.stream().filter(eq->eq.getType()==Column.CALL.getColumn()).collect(Collectors.toList());
				List<StockOptionsEquity> callDB = (List<StockOptionsEquity>) stockOptionsequityRepository.saveAll(call);
				
				List<StockOptionsEquity> put = equities.stream().filter(eq->eq.getType()==Column.PUT.getColumn()).collect(Collectors.toList());
				put.stream().forEach(putEq->{
					Optional<StockOptionsEquity> eqty = call.stream()
							.filter(pre -> pre.getRowNo() == putEq.getRowNo()).findFirst();
					if (eqty.isPresent()) {
						putEq.setPutId(eqty.get().getId());
					}
				});
				stockOptionsequityRepository.saveAll(put);
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
