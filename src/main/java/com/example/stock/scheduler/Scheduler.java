package com.example.stock.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.stock.constants.Constant;
import com.example.stock.service.ConfigService;
import com.example.stock.service.IntraDayNiftyService;
import com.example.stock.service.IntraDayStockOptionsService;
import com.example.stock.service.NiftyEquityService;
import com.example.stock.service.NiftyPremiumDKService;
import com.example.stock.service.StockOptionsEquityService;
import com.example.stock.service.StockService;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.HTTPConnection;

@Component
public class Scheduler {

	@Autowired
	StockService stockService;
	
	@Autowired
	NiftyEquityService equityService;
	
	@Autowired
	private StockOptionsEquityService equityLookupService;

	@Autowired
	private NiftyPremiumDKService niftyPremiumDKService;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	private IntraDayNiftyService intraDayEquityService;
	
	@Autowired
	private StockOptionsEquityService stockOptionsEquityService;
	
	@Autowired
	private IntraDayStockOptionsService intraDayStockOptionsEquityService;

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private final static Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	@Scheduled(cron = "0/1 * * * * ?") //Runs job for every second daily
	public void cronJobForNiftyPremiumDk() throws Exception {
		
		Map<String, Object> config = configService.getConfig();
		List<Integer> triggers = (List<Integer>) config.get(Constant.TRIGGER_RANGE);

		String niftyPrice = "https://www.nseindia.com/homepage/Indices1.json";

		JSONObject response = HTTPConnection.send(niftyPrice);

		JSONArray array = response.getJSONArray("data");
		//Double value = 0d;
		for (int j = 0; j < array.length(); j++) {
			JSONObject jsonobj = (JSONObject) array.get(j);
			if (jsonobj.get("name").equals("NIFTY 50")) {
				
				Double currentVal = Double.parseDouble(jsonobj.get("lastPrice").toString().replace(",", ""));
				int value = currentVal.intValue();
					int lastValue = 0;
					
					if(!config.isEmpty()) {
						if (config.containsKey(Constant.TRIGGER_LAST_VALUE))
							lastValue = Integer.parseInt(config.get(Constant.TRIGGER_LAST_VALUE).toString());
						
						int nearestValue = triggers.stream()
					            .min(Comparator.comparingInt(i -> Math.abs(i - value))).orElse(0);
						
						if (triggers.contains(nearestValue) && nearestValue !=lastValue) {
							//EquityDerivativesUtil.getExpiryDate(null);
							Map<String, Object> triggerValue = new HashMap<String, Object>();
							triggerValue.put(Constant.TRIGGER_LAST_VALUE, nearestValue);
							configService.saveConfig(triggerValue);
							niftyPremiumDKService.saveNiftyPremiumDK(currentVal);

							logger.info("Matched trigger range "+dateTimeFormatter.format(LocalDateTime.now()));
							break;
						}
					}
				break;
			}
		}
		//TimeUnit.SECONDS.sleep(2);
	}
	
	@Scheduled(cron = "0 */1 * ? * *") //Runs job after every 8 Min daily
	public void cronGetEquityDataForNifty() throws Exception {
		EquityDerivativesUtil.getExpiryDate(null);
		logger.info("Scheduler Job : Started cronGetEquityDataForNifty "+dateTimeFormatter.format(LocalDateTime.now()));
		intraDayEquityService.saveIntraDayNiftyEquityDerivatives();
		logger.info("Scheduler Job : Finished cronGetEquityDataForNifty "+dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	@Scheduled(cron = "0 0 17 1/1 * ?") //Runs job at 5 PM daily
	public void cronGetEquityDataForAllSymbol() throws Exception {
		logger.info("Scheduler Job : Started cronGetEquityDataForAllSymbol "+dateTimeFormatter.format(LocalDateTime.now()));
		equityLookupService.saveStockOptionsEquity();
		logger.info("Scheduler Job : Finished cronGetEquityDataForAllSymbol "+dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	@Scheduled(cron = "0 */8 * ? * *") //Runs job after every 8 Min daily
	public void cronIntraDayGetEquityDataForStockOption() throws Exception {
		logger.info("Scheduler Job : Started cronIntraDayGetEquityDataForStockOption "+dateTimeFormatter.format(LocalDateTime.now()));
		stockOptionsEquityService.saveStockOptionsEquity();
		logger.info("Scheduler Job : Finished cronIntraDayGetEquityDataForStockOption "+dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	@Scheduled(cron = "0 0 17 1/1 * ?") //Runs job at 5 PM daily
	public void cronGetEquityDataForStockOptionEOD() throws Exception {
		logger.info("Scheduler Job : Started cronGetEquityDataForStockOptionEOD "+dateTimeFormatter.format(LocalDateTime.now()));
		intraDayStockOptionsEquityService.saveIntraDayStockOptionEquityDerivatives();
		logger.info("Scheduler Job : Finished cronGetEquityDataForStockOptionEOD "+dateTimeFormatter.format(LocalDateTime.now()));
	}
}
