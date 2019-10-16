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
import com.example.stock.bean.IntraDayEquity;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.enums.Column;
import com.example.stock.repo.IntraDayNiftyEquityRepository;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IntraDayEquityService {
	
	private final static Logger logger = LoggerFactory.getLogger(IntraDayEquityService.class);
	
	@Autowired
	IntraDayNiftyEquityRepository intraDayNiftyEquityRepository;
	
	@Autowired
	private ConfigService configService;
	
	public void saveIntraDayNiftyEquityDerivatives() {
		List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(Constant.EQUITY_CHART_URL);
		List<IntraDayEquity> equities = new ArrayList<IntraDayEquity>();
		Date createdDate = DateUtil.getDateWithoutSec( new Date());
		resultObj.forEach(s -> {
			ObjectMapper mapper = new ObjectMapper();
			IntraDayEquity equity = new IntraDayEquity();
			equity = mapper.convertValue(s, IntraDayEquity.class);
			equity.setDate(createdDate);
			equity.setId(null);
			double posVol = Double.valueOf(equity.getChnginOI()) / Double.valueOf(equity.getVolume());
			equity.setPostionsVol(posVol);
			equities.add(equity);
		});
		String sourceDir = (String) configService.getConfigByName(Constant.INTRADAY_NIFTY_SOURCE_DIR);
		FileUtil.saveAsJsonFile(equities, sourceDir);
		intraDayNiftyEquityRepository.saveAll(equities);
	}

	
	public List<IntraDayEquity> serachIntraDayNiftyEquity(SearchFilter search) {

		List<IntraDayEquity> finalEquity = new ArrayList<IntraDayEquity>();
		try {
			Date startDate = DateUtil.addDaysToDate(-1);
			Date endDate = DateUtil.getCurretDate();
			if (search.getStartDate() != null) {
				startDate = DateUtil.getDateWithoutTime(search.getStartDate());
				if(search.getEndDate() !=null)
					endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			}
			List<Date> dates = intraDayNiftyEquityRepository.getDistinctDateBetweenRange(startDate, endDate);
			for (Date date : dates) {
				finalEquity.addAll(getEquitiesByDates(date, date, search));
			}
		} catch (Exception e) {
			logger.error("EquityService : Error",e);
		}
		
		return finalEquity;
	}

	
	public List<IntraDayEquity> getIntraDayYesterdayMinusToday(SearchFilter search) {
		Date startDate = search.getStartDate();
		Date endDate = search.getEndDate();

		try {
			List<Date> dates = intraDayNiftyEquityRepository.getDistinctDateBetweenRange(startDate, endDate);
			List<IntraDayEquity> searchedEquity = new ArrayList<IntraDayEquity>();
			for (Date date : dates) {
				Date thisDate = date;
				Date prevDate = DateUtil.addMinutesToDate(thisDate,-8);
				List<IntraDayEquity> thisEqty = getEquitiesByDates(thisDate, thisDate, search);
				List<IntraDayEquity> prevEqty = getEquitiesByDates(prevDate, prevDate, search);
				thisEqty.stream().forEach(thseq -> {

					Optional<IntraDayEquity> eqty = prevEqty.stream()
							.filter(pre -> pre.getStrikePrice() == thseq.getStrikePrice()).findFirst();
					if (eqty.isPresent()) {
						IntraDayEquity diffEqty = eqty.get();
						diffEqty.setOi(thseq.getOi() - eqty.get().getOi());
						diffEqty.setChnginOI(thseq.getChnginOI() - eqty.get().getChnginOI());
						diffEqty.setIv(thseq.getIv() - eqty.get().getIv());
						diffEqty.setLtp(thseq.getLtp() - eqty.get().getLtp());
						//diffEqty.setPrevEquity(null);
						thseq.setPrevEquity(diffEqty);
					}
				});

				searchedEquity.addAll(thisEqty);
			}
			return searchedEquity;
		} catch (Exception e) {
			logger.error("EquityServiceImpl: Error",e);
		}
		return null;
	}

	private List<IntraDayEquity> getEquitiesByDates(Date startDate, Date endDate, SearchFilter search) throws Exception{
		List<Filter> filters = search.getFilter();

		List<IntraDayEquity> finalEquity = new ArrayList<IntraDayEquity>();
		if (search.getStrikePrice() > 0) {
			List<IntraDayEquity> equities = intraDayNiftyEquityRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.valueOf(search.getType()).ordinal());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<IntraDayEquity> equities = intraDayNiftyEquityRepository.getAllEquitiesBetweenDatesAndByType(startDate,
				endDate, Column.valueOf(search.getType()).ordinal(), search.getSymbol());

		for (Filter filt : filters) {
			switch (filt.getKey()) {
			case "OI":
				finalEquity
						.addAll(equities.stream().sorted(Comparator.comparing(IntraDayEquity::getOi).reversed())
								.limit(search.getRecordCount()).collect(Collectors.toList()));
				break;
			case "CHANGE_IN_OI":
				List<IntraDayEquity> chngEquities = getChninOISort(equities);
				List<IntraDayEquity> sortedList = chngEquities.stream()
						.sorted(Comparator.comparing(IntraDayEquity::getChnginOI).reversed())
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

		return finalEquity.stream().filter(EquityDerivativePredicate.distinctByKeys(IntraDayEquity::getRowNo,
				IntraDayEquity::getDate)).collect(Collectors.toList());
	}
	
	private List<IntraDayEquity> getChninOISort(List<IntraDayEquity> equities) {

		List<IntraDayEquity> list = new ArrayList<IntraDayEquity>();
		equities.forEach(s -> {
			IntraDayEquity e = s;
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
