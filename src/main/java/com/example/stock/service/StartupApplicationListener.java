package com.example.stock.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.stock.constants.Constant;
import com.example.stock.util.FileUtil;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
 
	@Autowired 
	private ConfigService configService;
	
	@Autowired
	private DBDataLoaderService dbDataLoaderService;
 
    @Override 
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	HashMap<String, Object> config = configService.loadConfigFromFile();
    	String stockSourceDir =  config.get(Constant.STOCK_SOURCE_DIR).toString();
    	String niftySourceDir =  config.get(Constant.NIFTY_SOURCE_DIR).toString();
    	String fiiSourceDir =  config.get(Constant.FII_SOURCE_DIR).toString();
    	String stockOptionsSourceDir =  config.get(Constant.STOCK_OPTIONS_SOURCE_DIR).toString();
    	String path = FileUtil.createDir(stockSourceDir);
    	config.put(Constant.STOCK_SOURCE_DIR, path);
    	path = FileUtil.createDir(niftySourceDir);
    	config.put(Constant.NIFTY_SOURCE_DIR, path);
    	path = FileUtil.createDir(fiiSourceDir);
    	config.put(Constant.FII_SOURCE_DIR, path);
    	path = FileUtil.createDir(stockOptionsSourceDir);
    	config.put(Constant.STOCK_OPTIONS_SOURCE_DIR, path);
    	configService.saveConfig(config);
    	
    	// Load data from file to DB
    	dbDataLoaderService.loadStockDataToDB();
    	dbDataLoaderService.loadEquityOptionsToDB();
    	dbDataLoaderService.loadFIIDataToDB();
    }
}
