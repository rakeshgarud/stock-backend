package com.example.stock.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock.bean.IntraDayEquity;
import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.StockOptionsEquity;
import com.example.stock.dto.SearchFilter;
import com.example.stock.service.ConfigService;
import com.example.stock.service.EquityService;
import com.example.stock.service.IntraDayEquityService;
import com.example.stock.util.DateUtil;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = { "Content-Type", "Accept",
		"x-xsrf-token", "Access-Control-Allow-Headers", "Origin", "Access-Control-Request-Method",
		"Access-Control-Request-Headers" })
@RestController
@RequestMapping("/options")
public class EquityOptionsController {
	
	private final static Logger logger = LoggerFactory.getLogger(EquityOptionsController.class);
	
	@Autowired
	private EquityService equityService;
	
	@Autowired
	private IntraDayEquityService intraDayEquityService;
	
	@Autowired
	ConfigService configService; 
	
	@PostMapping(value="/search/nifty",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<NiftyEquityDerivative> getEquity(@RequestBody SearchFilter search) {
		try {
			return equityService.serachNiftyEquity(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/equity");
		}
		return Arrays.asList();
	}

	@PostMapping(value="/nifty/premiumdk",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<NiftyEquityDerivative> getPremiumDk(@RequestBody SearchFilter search) {
		try {
			return equityService.getNiftyPremiumDK(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/premiumdk");
		}
		return Arrays.asList();
	}
	
	@GetMapping("/load-nifty")
	public void loadEquityData(@PathParam("url") String url) {
		equityService.saveNiftyEquityDerivatives();
	}
	
	@GetMapping("/load-stocks")
	public void loadStockOptionsData() throws Exception{
		equityService.saveStockOptionsEquity();
	}
	
	@PostMapping(value="/search/stocks",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<StockOptionsEquity> getStockOptionsEquity(@RequestBody SearchFilter search) {
		try {
			return equityService.serachStocksOptionEquity(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/stocks");
		}
		return Arrays.asList();
	}
	
	@PostMapping(value="/intraday",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<IntraDayEquity> getIntraDayPremiumDk(@PathParam("startTime") String startTime,@PathParam("endTime") String endTime,@RequestBody SearchFilter search) {
		try {
			LocalTime intime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("h:m a"));
			String outtime = intime.format(DateTimeFormatter.ISO_LOCAL_TIME);
			int hr = Integer.parseInt(outtime.split(":")[0]);
			int min = Integer.parseInt(outtime.split(":")[1]);
			search.setStartDate(DateUtil.setDateWithTime(hr,min));
			intime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("h:m a"));
			outtime = intime.format(DateTimeFormatter.ISO_LOCAL_TIME);
			hr = Integer.parseInt(outtime.split(":")[0]);
			min = Integer.parseInt(outtime.split(":")[1]);
			search.setEndDate(DateUtil.setDateWithTime(hr,min));
			return intraDayEquityService.getIntraDayNiftyPremiumDK(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /intraday");
		}
		return Arrays.asList();
	}
}
