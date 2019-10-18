package com.example.stock.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock.bean.EquityDerivativePredicate;
import com.example.stock.bean.NiftyPremiumDK;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.enums.Column;
import com.example.stock.repo.NiftyPremiumDKRepository;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NiftyPremiumDKService {
	
	private final static Logger logger = LoggerFactory.getLogger(NiftyPremiumDKService.class);
	
	@Autowired
	NiftyPremiumDKRepository niftyPremiumDKRepository ;
	
	@Autowired
	private ConfigService configService;
	
	public void saveNiftyPremiumDK(double currentPrice) {
		List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(Constant.EQUITY_CHART_URL);
		List<NiftyPremiumDK> equities = new ArrayList<NiftyPremiumDK>();
		Date createdDate = DateUtil.getDateWithoutSec( new Date());
		resultObj.forEach(s -> {
			ObjectMapper mapper = new ObjectMapper();
			NiftyPremiumDK equity = new NiftyPremiumDK();
			equity = mapper.convertValue(s, NiftyPremiumDK.class);
			equity.setDate(createdDate);
			equity.setId(null);
			equity.setPostionsVol(equity.getChnginOI() / equity.getVolume());
			equity.setCurrentPrice(currentPrice);
			equities.add(equity);
		});
		String sourceDir = (String) configService.getConfigByName(Constant.NIFTY_PRIMIUMDK_SOURCE_DIR);
		FileUtil.saveAsJsonFile(equities, sourceDir);
		niftyPremiumDKRepository.saveAll(equities);
	}

	
	public List<NiftyPremiumDK> serachIntraDayNiftyEquity(SearchFilter search) {

		List<NiftyPremiumDK> finalEquity = new ArrayList<NiftyPremiumDK>();
		try {
			Date startDate = DateUtil.addDaysToDate(-1);
			Date endDate = DateUtil.getCurretDate();
			if (search.getStartDate() != null) {
				startDate = DateUtil.getDateWithoutTime(search.getStartDate());
				if(search.getEndDate() !=null)
					endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			}
			List<Date> dates = niftyPremiumDKRepository.getDistinctDateBetweenRange(startDate, endDate);
			for (Date date : dates) {
				finalEquity.addAll(getEquitiesByDates(date, date, search));
			}
		} catch (Exception e) {
			logger.error("NiftyPremiumDKService : Error",e);
		}
		
		return finalEquity;
	}

	
	public List<NiftyPremiumDK> getMonthyYesterdayMinusToday(SearchFilter search) {
		Date startDate = search.getStartDate();
		Date endDate = search.getEndDate();

		try {
			List<Date> dates = niftyPremiumDKRepository.getDistinctDateBetweenRange(startDate, endDate);
			List<NiftyPremiumDK> searchedEquity = new ArrayList<NiftyPremiumDK>();
			for (Date date : dates) {
				List<NiftyPremiumDK> niftyPremium = niftyPremiumDKRepository.getEquitiesBetweenDatesAndByType(date, date);
				double currentPrice = getNearestPrice(niftyPremium.get(0).getCurrentPrice());
				search.setStrikePrice(currentPrice);
				searchedEquity.addAll(getEquitiesByDates(date, date, search));
			}
			return searchedEquity;
		} catch (Exception e) {
			logger.error("NiftyPremiumDKService: Error",e);
		}
		return null;
	}

	private List<NiftyPremiumDK> getEquitiesByDates(Date startDate, Date endDate, SearchFilter search) throws Exception{
		List<Filter> filters = search.getFilter();

		List<NiftyPremiumDK> finalEquity = new ArrayList<NiftyPremiumDK>();
		if (search.getStrikePrice() > 0) {
			List<NiftyPremiumDK> equities = niftyPremiumDKRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.valueOf(search.getType()).ordinal());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<NiftyPremiumDK> equities = niftyPremiumDKRepository.getAllEquitiesBetweenDatesAndByType(startDate,
				endDate, Column.valueOf(search.getType()).ordinal(), search.getSymbol());

		for (Filter filt : filters) {
			switch (filt.getKey()) {
			case "OI":
				finalEquity
						.addAll(equities.stream().sorted(Comparator.comparing(NiftyPremiumDK::getOi).reversed())
								.limit(search.getRecordCount()).collect(Collectors.toList()));
				break;
			case "CHANGE_IN_OI":
				List<NiftyPremiumDK> chngEquities = getChninOISort(equities);
				List<NiftyPremiumDK> sortedList = chngEquities.stream()
						.sorted(Comparator.comparing(NiftyPremiumDK::getChnginOI).reversed())
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

		return finalEquity.stream().filter(EquityDerivativePredicate.distinctByKeys(NiftyPremiumDK::getRowNo,
				NiftyPremiumDK::getDate)).collect(Collectors.toList());
	}
	
	private List<NiftyPremiumDK> getChninOISort(List<NiftyPremiumDK> equities) {

		List<NiftyPremiumDK> list = new ArrayList<NiftyPremiumDK>();
		equities.forEach(s -> {
			NiftyPremiumDK e = s;
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
		
		int nearestValue = triggers.stream()
	            .min(Comparator.comparingInt(i -> Math.abs(i - (int)currentPrice))).orElse(0);
		return nearestValue;
	}
}
