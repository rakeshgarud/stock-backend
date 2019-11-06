package com.example.stock.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock.bean.IntraDayNifty;
import com.example.stock.bean.IntraDayStockOption;
import com.example.stock.bean.MonthlyEquity;
import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.StockOptionsEquity;
import com.example.stock.dto.SearchFilter;
import com.example.stock.service.ConfigService;
import com.example.stock.service.IntraDayNiftyService;
import com.example.stock.service.IntraDayStockOptionsService;
import com.example.stock.service.MonthlyEquityService;
import com.example.stock.service.NiftyEquityService;
import com.example.stock.service.NiftyPremiumDKService;
import com.example.stock.service.StockOptionsEquityService;
import com.example.stock.util.DateUtil;

@CrossOrigin(origins = {"*"}, maxAge = 3600, allowedHeaders = { "Content-Type", "Accept",
		"x-xsrf-token", "Access-Control-Allow-Headers", "Origin", "Access-Control-Request-Method",
		"Access-Control-Request-Headers" })
@RestController
@RequestMapping("/options")
public class EquityOptionsController {
	
	private final static Logger logger = LoggerFactory.getLogger(EquityOptionsController.class);
	
	@Autowired
	private NiftyEquityService equityService;
	
	@Autowired
	private NiftyPremiumDKService niftyPremiumDKService;
	
	@Autowired
	ConfigService configService; 
	
	@Autowired
	private IntraDayNiftyService intraDayEquityService;
	
	@Autowired
	private MonthlyEquityService monthlyEquityService;
	
	@Autowired
	private StockOptionsEquityService stockOptionsEquityService;
	
	@Autowired
	private IntraDayStockOptionsService intraDayStockOptionsEquityService;
	
	@PostMapping(value="/search/nifty",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<NiftyEquityDerivative> getEquity(@RequestBody SearchFilter search) {
		try {
			return equityService.serachNiftyEquity(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/equity");
		}
		return Arrays.asList();
	}

	@PostMapping(value="/nifty/yesterday-today",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<NiftyEquityDerivative> getYesterdayMinusTodayK(@RequestBody SearchFilter search) {
		try {
			return equityService.getYesterdayMinusTodayK(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/yesterday-today");
		}
		return Arrays.asList();
	}
	
	@PostMapping(value="/search/stocksOptions",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<StockOptionsEquity> getStockOptionsEquity(@RequestBody SearchFilter search) {
		try {
			return stockOptionsEquityService.serachStocksOptionEquity(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/stocks");
		}
		return Arrays.asList();
	}
	
	@PostMapping(value="/stocksOptions.csv",consumes = MediaType.APPLICATION_JSON_VALUE,produces = "text/csv; charset=utf-8")
	public ResponseEntity<byte[]> exportStockOptionsCSV(@RequestBody SearchFilter search) {
		try {
			String csvString = stockOptionsEquityService.getCsvReport(search);
			byte[] isr = csvString.getBytes();
			String fileName = "employees.json";
			HttpHeaders respHeaders = new HttpHeaders();
			respHeaders.setContentLength(isr.length);
			respHeaders.setContentType(new MediaType("text", "json"));
			respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/stocks");
		}
		return new ResponseEntity<byte[]>(null, null, HttpStatus.OK);
	}
	
	@GetMapping("/load-nifty")
	public void loadEquityData(@PathParam("url") String url) {
		equityService.saveNiftyEquityDerivatives();
	}
	
	@GetMapping("/load-stocksOptions")
	public void loadStockOptionsData() throws Exception{
		stockOptionsEquityService.saveStockOptionsEquity();
	}
	
	@PostMapping(value="/nifty/intraday",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<IntraDayNifty> getIntraDayYesterDayMinusToday(@PathParam("startTime") String startTime,@PathParam("endTime") String endTime,@RequestBody SearchFilter search) {
		try {
			int hr = Integer.parseInt(startTime.split(":")[0]);
			if(hr>12)
				hr = hr-12;
			int min = Integer.parseInt(startTime.split(":")[1]);
			search.setStartDate(DateUtil.setDateWithTime(hr,min));
			hr = Integer.parseInt(endTime.split(":")[0]);
			if(hr>12)
				hr = hr-12;
			min = Integer.parseInt(endTime.split(":")[1]);
			search.setEndDate(DateUtil.setDateWithTime(hr,min));
			return intraDayEquityService.getIntraDayYesterdayMinusToday(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /nifty/intraday");
		}
		return Arrays.asList();
	}
	
	@PostMapping(value="/monthly/equity",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<MonthlyEquity> getMonthlyYesterDayMinusToday(@PathParam("startTime") String startTime,@PathParam("endTime") String endTime,@RequestBody SearchFilter search) {
		try {
			int hr = Integer.parseInt(startTime.split(":")[0]);
			int min = Integer.parseInt(startTime.split(":")[1]);
			search.setStartDate(DateUtil.setDateWithTime(hr,min));
			hr = Integer.parseInt(endTime.split(":")[0]);
			min = Integer.parseInt(endTime.split(":")[1]);
			search.setEndDate(DateUtil.setDateWithTime(hr,min));
			return monthlyEquityService.getMonthyYesterdayMinusToday(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /intraday");
		}
		return Arrays.asList();
	}
	
	@PostMapping(value="/search/premium-decay",consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object getPremiumDecay(@RequestBody SearchFilter search) {
		try {
			return niftyPremiumDKService.getPremiumDK(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /search/PremiumDecay");
		}
		return new HashMap<String, Object>();
	}
	
	@PostMapping(value="/stockOptions/yesterday-today",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<StockOptionsEquity> getStocksOptionsYesterdayMinusTodayK(@RequestBody SearchFilter search) {
		try {
			return stockOptionsEquityService.getYesterdayMinusTodayK(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /stockOptions/yesterday-today");
		}
		return Arrays.asList();
	}
	
	@PostMapping(value="/stockOptions/intraday",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<IntraDayStockOption> getStocksOptionsIntraDay(@PathParam("startTime") String startTime,@PathParam("endTime") String endTime,@RequestBody SearchFilter search) {
		try {
			int hr = Integer.parseInt(startTime.split(":")[0]);
			int min = Integer.parseInt(startTime.split(":")[1]);
			search.setStartDate(DateUtil.setDateWithTime(hr,min));
			hr = Integer.parseInt(endTime.split(":")[0]);
			min = Integer.parseInt(endTime.split(":")[1]);
			search.setEndDate(DateUtil.setDateWithTime(hr,min));
			return intraDayStockOptionsEquityService.getIntraDayYesterdayMinusToday(search);
		} catch (Exception e) {
			logger.error("Error while processing request- /stockOptions/intraday");
		}
		return Arrays.asList();
	}
}
