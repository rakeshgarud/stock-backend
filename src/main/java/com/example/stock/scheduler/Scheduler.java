package com.example.stock.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.stock.constants.Constant;
import com.example.stock.service.ConfigService;
import com.example.stock.service.EquityService;
import com.example.stock.service.StockOptionsEquityLookupService;
import com.example.stock.service.StockService;
import com.example.stock.util.HTTPConnection;

@Component
public class Scheduler {

	@Autowired
	StockService stockService;
	
	@Autowired
	EquityService equityService;
	
	@Autowired
	private StockOptionsEquityLookupService equityLookupService;

	@Autowired
	ConfigService configService;

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private final static Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	@Scheduled(cron = "0/1 * * * * ?") //Runs job for every second daily
	public void cronJobForNifty() throws Exception {
		
		Map<String, Object> config = configService.getConfig();
		List<Double> triggers = (List<Double>) config.get(Constant.TRIGGER_RANGE);

		String niftyPrice = "https://www.nseindia.com/homepage/Indices1.json";

		JSONObject response = HTTPConnection.send(niftyPrice);

		JSONArray array = response.getJSONArray("data");
		Double value = 0d;
		for (int j = 0; j < array.length(); j++) {
			JSONObject jsonobj = (JSONObject) array.get(j);
			if (jsonobj.get("name").equals("NIFTY 50")) {
				value = Double.parseDouble(jsonobj.get("lastPrice").toString().replace(",", ""));
					Double lastValue = 0d;
					
					if(!config.isEmpty()) {
						if (config.containsKey(Constant.TRIGGER_LAST_VALUE))
							lastValue = Double.parseDouble(config.get(Constant.TRIGGER_LAST_VALUE).toString());
						
						if (triggers.contains(value) && !value.equals(lastValue)) {
							Map<String, Object> triggerValue = new HashMap<String, Object>();
							triggerValue.put(Constant.TRIGGER_LAST_VALUE, value);
							configService.saveConfig(triggerValue);
							equityService.saveNiftyEquityDerivatives();

							logger.info("Matched trigger range "+dateTimeFormatter.format(LocalDateTime.now()));
							break;
						}
					}
				break;
			}
		}
		TimeUnit.SECONDS.sleep(2);
	}
	
	@Scheduled(cron = "0 8 * * * ?") //Runs job after every 8 Min daily
	public void cronGetEquityDataForNifty() throws Exception {
		logger.info("Scheduler Job : Started cronGetEquityDataForNifty "+dateTimeFormatter.format(LocalDateTime.now()));
		//equityService.saveNiftyEquityDerivatives();
		logger.info("Scheduler Job : Finished cronGetEquityDataForNifty "+dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	@Scheduled(cron = "0 0 17 1/1 * ?") //Runs job at 5 PM daily
	public void cronGetEquityDataForAllSymbol() throws Exception {
		logger.info("Scheduler Job : Started cronGetEquityDataForAllSymbol "+dateTimeFormatter.format(LocalDateTime.now()));
		equityLookupService.loadStocksOptionsData();
		logger.info("Scheduler Job : Finished cronGetEquityDataForAllSymbol "+dateTimeFormatter.format(LocalDateTime.now()));
	}
}
