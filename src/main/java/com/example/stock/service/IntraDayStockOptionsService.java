package com.example.stock.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock.bean.EquityDerivativePredicate;
import com.example.stock.bean.IntraDayStockOption;
import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.enums.Column;
import com.example.stock.repo.IntraDayStockOptionsRepository;
import com.example.stock.util.CommonUtil;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IntraDayStockOptionsService {
	
	private final static Logger logger = LoggerFactory.getLogger(IntraDayStockOptionsService.class);
	
	@Autowired
	IntraDayStockOptionsRepository intraDayRepository;
	
	@Autowired
	private ConfigService configService;
	
	public void saveIntraDayStockOptionEquityDerivatives() {
		 List<HashMap<String, Object>> symbols = configService.getFileAsList("stock-list.json");
	       	for(HashMap<String, Object> symbol : symbols) {
	       		String url = Constant.EQUITY_CHART_URL.split("=")[0]+"="+symbol.get("symbol");
	    		List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(url);
	    		List<IntraDayStockOption> equities = new ArrayList<IntraDayStockOption>();
	    		List<IntraDayStockOption> call = new ArrayList<IntraDayStockOption>();
	    		List<IntraDayStockOption> put = new ArrayList<IntraDayStockOption>();
	    		
	    		Date createdDate = DateUtil.getDateWithoutSec( new Date());
	    		resultObj.forEach(s -> {
	    			ObjectMapper mapper = new ObjectMapper();
	    			IntraDayStockOption equity = new IntraDayStockOption();
	    			equity = mapper.convertValue(s, IntraDayStockOption.class);
	    			equity.setDate(createdDate);
	    			equity.setId(null);
	    			double posVol = Double.valueOf(equity.getChnginOI()) / Double.valueOf(equity.getVolume());
	    			equity.setPostionsVol(posVol);
	    			equities.add(equity);
	    			
	    			if(equity.getType()==Column.CALL.getColumn())
						call.add(equity);
					if(equity.getType()==Column.PUT.getColumn())
						put.add(equity);
	    		});
	    		//String sourceDir = (String) configService.getConfigByName(Constant.INTRADAY_NIFTY_SOURCE_DIR);
	    		//FileUtil.saveAsJsonFile(equities, sourceDir);
	    		List<IntraDayStockOption> callDB = (List<IntraDayStockOption>) intraDayRepository.saveAll(call);
	    		put.stream().forEach(putEq->{
					Optional<IntraDayStockOption> eqty = callDB.stream()
							.filter(pre -> pre.getRowNo() == putEq.getRowNo()).findFirst();
					if (eqty.isPresent()) {
						putEq.setPutId(eqty.get().getId());
					}
				});
	    		intraDayRepository.saveAll(put);
	       	}
	}

	
	public List<IntraDayStockOption> serachIntraDayStockOptionEquity(SearchFilter search) {

		List<IntraDayStockOption> finalEquity = new ArrayList<IntraDayStockOption>();
		try {
			Date startDate = DateUtil.addDaysToDate(-1);
			Date endDate = DateUtil.getCurretDate();
			if (search.getStartDate() != null) {
				startDate = DateUtil.getDateWithoutTime(search.getStartDate());
				if(search.getEndDate() !=null)
					endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			}
			List<Date> dates = intraDayRepository.getDistinctDateBetweenRange(startDate, endDate);
			for (Date date : dates) {
				finalEquity.addAll(getEquitiesByDates(date, date, search));
			}
		} catch (Exception e) {
			logger.error("IntraDayEquityService : Error",e);
		}
		
		return finalEquity;
	}

	
	public List<IntraDayStockOption> getIntraDayYesterdayMinusToday(SearchFilter search) {
		Date startDate = search.getStartDate();
		Date endDate = search.getEndDate();

		try {
			List<Date> dates = intraDayRepository.getDistinctDateBetweenRange(startDate, endDate);
			List<IntraDayStockOption> searchedEquity = new ArrayList<IntraDayStockOption>();
			if(!dates.isEmpty()) {
				Date lastDate = dates.get(dates.size()-1);
				List<IntraDayStockOption> thisEqty = getEquitiesByDates(dates.get(0), dates.get(0), search);
				List<IntraDayStockOption> prevEqty = getEquitiesByDates(lastDate, lastDate, search);
				thisEqty.stream().forEach(thseq -> {

					Optional<IntraDayStockOption> eqty = prevEqty.stream()
							.filter(pre -> pre.getStrikePrice() == thseq.getStrikePrice()).findFirst();
					if (eqty.isPresent()) {
						IntraDayStockOption diffEqty = eqty.get();
						diffEqty.setOi(thseq.getOi() - eqty.get().getOi());
						diffEqty.setChnginDif(thseq.getChnginOI() - eqty.get().getChnginOI());
						diffEqty.setIv(thseq.getIv() - eqty.get().getIv());
						diffEqty.setLtp(thseq.getLtp() - eqty.get().getLtp());
						diffEqty.setVolumeDif(thseq.getVolume() - eqty.get().getVolume());
						diffEqty.setPostionsVol(diffEqty.getChnginDif()/diffEqty.getVolumeDif());
						thseq.setPrevEquity(diffEqty);
					}
				});

				searchedEquity.addAll(thisEqty);
			}
			return searchedEquity;
		} catch (Exception e) {
			logger.error("IntraDayEquityService: Error",e);
		}
		return null;
	}

	public HashMap<String, Object> getPremiumDK(SearchFilter search) {
		Date startDate = DateUtil.getDateWithoutTime(search.getStartDate());
		Date endDate = search.getEndDate();
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			List<Date> dates = intraDayRepository.getDistinctDateBetweenRange(startDate, endDate);
			List<IntraDayStockOption> searchedEquity = new ArrayList<IntraDayStockOption>();
			for (Date date : dates) {
				searchedEquity.addAll(getEquitiesByDates(date, date, search));
			}
			List<Double> IVList = searchedEquity.stream().map(x -> x.getIv()).collect(Collectors.toList());
			List<Double> stikePriceList = searchedEquity.stream().map(x -> x.getStrikePrice())
					.collect(Collectors.toList());
			response.put("Data", searchedEquity);
			response.put("corelation", CommonUtil.getCorrelationCoefficient(IVList,stikePriceList));
			return response;
		} catch (Exception e) {
			logger.error("IntraDayEquityService: Error", e);
		}
		return null;
	}
	
	private List<IntraDayStockOption> getEquitiesByDates(Date startDate, Date endDate, SearchFilter search) throws Exception{
		List<Filter> filters = search.getFilter();

		List<IntraDayStockOption> finalEquity = new ArrayList<IntraDayStockOption>();
		if (search.getStrikePrice() > 0) {
			List<IntraDayStockOption> equities = intraDayRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.valueOf(search.getType()).getColumn());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<IntraDayStockOption> equities = intraDayRepository.getAllEquitiesBetweenDatesAndByType(startDate,
				endDate, Column.valueOf(search.getType()).getColumn(), search.getSymbol());

		for (Filter filt : filters) {
			switch (filt.getKey()) {
			case "OI":
				finalEquity
						.addAll(equities.stream().sorted(Comparator.comparing(IntraDayStockOption::getOi).reversed())
								.limit(search.getRecordCount()).collect(Collectors.toList()));
				break;
			case "CHANGE_IN_OI":
				List<IntraDayStockOption> chngEquities = getChninOISort(equities);
				List<IntraDayStockOption> sortedList = chngEquities.stream()
						.sorted(Comparator.comparing(IntraDayStockOption::getChnginOI).reversed())
						.limit(search.getRecordCount()).collect(Collectors.toList());
				finalEquity.addAll(equities.stream()
						.filter(line -> sortedList.stream().anyMatch(s -> s.getRowNo() == line.getRowNo()))
						.collect(Collectors.toList()));
				break;
			case "STRIKE_PRICE":
				finalEquity.addAll(equities.stream().filter(line -> line.getStrikePrice() == filt.getValue())
						.collect(Collectors.toList()));
				break;
			}

		}
		if (filters.isEmpty()) {
			finalEquity = equities.stream().limit(search.getRecordCount()).collect(Collectors.toList());
		}

		return finalEquity.stream().filter(EquityDerivativePredicate.distinctByKeys(IntraDayStockOption::getRowNo,
				IntraDayStockOption::getDate)).collect(Collectors.toList());
	}
	
	private List<IntraDayStockOption> getChninOISort(List<IntraDayStockOption> equities) {

		List<IntraDayStockOption> list = new ArrayList<IntraDayStockOption>();
		equities.forEach(s -> {
			IntraDayStockOption e = s;
			Double d = s.getChnginOI();
			if (s.getChnginOI() < 0) {
				d = s.getChnginOI() * -1;
			}
			e.setChnginOI(d);
			list.add(e);
		});

		return list;
	}
	
	private double getNearestPrice(double currentPrice) {
		Map<String, Object> config = configService.getConfig();
		List<Integer> triggers = (List<Integer>) config.get(Constant.TRIGGER_RANGE);

		int nearestValue = triggers.stream().min(Comparator.comparingInt(i -> Math.abs(i - (int) currentPrice)))
				.orElse(0);
		return nearestValue;
	}
}
