package com.example.stock.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock.bean.EquityDerivativePredicate;
import com.example.stock.bean.MonthlyEquity;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.enums.Column;
import com.example.stock.repo.MonthlyNiftyEquityRepository;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MonthlyEquityService {
	
	private final static Logger logger = LoggerFactory.getLogger(MonthlyEquityService.class);
	
	@Autowired
	MonthlyNiftyEquityRepository monthlyNiftyEquityRepository;
	
	@Autowired
	private ConfigService configService;
	
	public void saveIntraDayNiftyEquityDerivatives() {
		List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(Constant.EQUITY_CHART_URL);
		List<MonthlyEquity> equities = new ArrayList<MonthlyEquity>();
		Date createdDate = DateUtil.getDateWithoutSec( new Date());
		resultObj.forEach(s -> {
			ObjectMapper mapper = new ObjectMapper();
			MonthlyEquity equity = new MonthlyEquity();
			equity = mapper.convertValue(s, MonthlyEquity.class);
			equity.setDate(createdDate);
			equity.setId(null);
			equity.setPostionsVol(equity.getChnginOI() / equity.getVolume());
			equities.add(equity);
		});
		String sourceDir = (String) configService.getConfigByName(Constant.INTRADAY_NIFTY_SOURCE_DIR);
		FileUtil.saveAsJsonFile(equities, sourceDir);
		monthlyNiftyEquityRepository.saveAll(equities);
	}

	
	public List<MonthlyEquity> serachIntraDayNiftyEquity(SearchFilter search) {

		List<MonthlyEquity> finalEquity = new ArrayList<MonthlyEquity>();
		try {
			Date startDate = DateUtil.addDaysToDate(-1);
			Date endDate = DateUtil.getCurretDate();
			if (search.getStartDate() != null) {
				startDate = DateUtil.getDateWithoutTime(search.getStartDate());
				if(search.getEndDate() !=null)
					endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			}
			List<Date> dates = monthlyNiftyEquityRepository.getDistinctDateBetweenRange(startDate, endDate);
			for (Date date : dates) {
				finalEquity.addAll(getEquitiesByDates(date, date, search));
			}
		} catch (Exception e) {
			logger.error("MonthlyEquityService : Error",e);
		}
		
		return finalEquity;
	}

	
	public List<MonthlyEquity> getMonthyYesterdayMinusToday(SearchFilter search) {
		Date startDate = search.getStartDate();
		Date endDate = search.getEndDate();

		try {
			List<Date> dates = monthlyNiftyEquityRepository.getDistinctDateBetweenRange(startDate, endDate);
			List<MonthlyEquity> searchedEquity = new ArrayList<MonthlyEquity>();
			for (Date date : dates) {
				Date thisDate = date;
				Date prevDate = DateUtil.addMinutesToDate(thisDate,-8);
				List<MonthlyEquity> thisEqty = getEquitiesByDates(thisDate, thisDate, search);
				List<MonthlyEquity> prevEqty = getEquitiesByDates(prevDate, prevDate, search);
				thisEqty.stream().forEach(thseq -> {

					Optional<MonthlyEquity> eqty = prevEqty.stream()
							.filter(pre -> pre.getStrikePrice() == thseq.getStrikePrice()).findFirst();
					if (eqty.isPresent()) {
						MonthlyEquity diffEqty = eqty.get();
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
			logger.error("MonthlyEquityService: Error",e);
		}
		return null;
	}

	private List<MonthlyEquity> getEquitiesByDates(Date startDate, Date endDate, SearchFilter search) throws Exception{
		List<Filter> filters = search.getFilter();

		List<MonthlyEquity> finalEquity = new ArrayList<MonthlyEquity>();
		if (search.getStrikePrice() > 0) {
			List<MonthlyEquity> equities = monthlyNiftyEquityRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.valueOf(search.getType()).ordinal());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<MonthlyEquity> equities = monthlyNiftyEquityRepository.getAllEquitiesBetweenDatesAndByType(startDate,
				endDate, Column.valueOf(search.getType()).ordinal(), search.getSymbol());

		for (Filter filt : filters) {
			switch (filt.getKey()) {
			case "OI":
				finalEquity
						.addAll(equities.stream().sorted(Comparator.comparing(MonthlyEquity::getOi).reversed())
								.limit(search.getRecordCount()).collect(Collectors.toList()));
				break;
			case "CHANGE_IN_OI":
				List<MonthlyEquity> chngEquities = getChninOISort(equities);
				List<MonthlyEquity> sortedList = chngEquities.stream()
						.sorted(Comparator.comparing(MonthlyEquity::getChnginOI).reversed())
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

		return finalEquity.stream().filter(EquityDerivativePredicate.distinctByKeys(MonthlyEquity::getRowNo,
				MonthlyEquity::getDate)).collect(Collectors.toList());
	}
	
	private List<MonthlyEquity> getChninOISort(List<MonthlyEquity> equities) {

		List<MonthlyEquity> list = new ArrayList<MonthlyEquity>();
		equities.forEach(s -> {
			MonthlyEquity e = s;
			Double d = s.getChnginOI();
			if (s.getChnginOI() < 0) {
				d = s.getChnginOI() * -1;
			}
			e.setChnginOI(d);
			list.add(e);
		});

		return list;
	}
}
