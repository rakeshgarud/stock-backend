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
import com.example.stock.bean.IntraDayNifty;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.enums.Column;
import com.example.stock.repo.IntraDayNiftyEquityRepository;
import com.example.stock.util.CommonUtil;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IntraDayNiftyService {
	
	private final static Logger logger = LoggerFactory.getLogger(IntraDayNiftyService.class);
	
	@Autowired
	IntraDayNiftyEquityRepository intraDayNiftyEquityRepository;
	
	@Autowired
	private ConfigService configService;
	
	public void saveIntraDayNiftyEquityDerivatives() {
		List<String> dates = EquityDerivativesUtil.getExpiryDate(null);
		for(String date:dates) {
			String url = Constant.EQUITY_CHART_URL + "&date=" + date;
			List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(url);
			List<IntraDayNifty> equities = new ArrayList<IntraDayNifty>();
			List<IntraDayNifty> call = new ArrayList<IntraDayNifty>();
			List<IntraDayNifty> put = new ArrayList<IntraDayNifty>();
			
			Date createdDate = DateUtil.getDateWithoutSec( new Date());
			resultObj.forEach(s -> {
				ObjectMapper mapper = new ObjectMapper();
				IntraDayNifty equity = new IntraDayNifty();
				equity = mapper.convertValue(s, IntraDayNifty.class);
				equity.setDate(createdDate);
				equity.setExpiryDate(date);
				equity.setId(null);
				double posVol = Double.valueOf(equity.getChnginOI()) / Double.valueOf(equity.getVolume());
				equity.setPostionsVol(posVol);
				equities.add(equity);
				if(equity.getType()==Column.CALL.getColumn())
					call.add(equity);
				if(equity.getType()==Column.PUT.getColumn())
					put.add(equity);
			});
			String sourceDir = (String) configService.getConfigByName(Constant.INTRADAY_NIFTY_SOURCE_DIR);
			FileUtil.saveAsJsonFile(equities, sourceDir);
			List<IntraDayNifty> callDB =  (List<IntraDayNifty>) intraDayNiftyEquityRepository.saveAll(call);
			
			put.stream().forEach(putEq->{
				Optional<IntraDayNifty> eqty = callDB.stream()
						.filter(pre -> pre.getRowNo() == putEq.getRowNo()).findFirst();
				if (eqty.isPresent()) {
					putEq.setPutId(eqty.get().getId());
				}
			});
			intraDayNiftyEquityRepository.saveAll(put);
		}
	}

	
	public List<IntraDayNifty> serachIntraDayNiftyEquity(SearchFilter search) {

		List<IntraDayNifty> finalEquity = new ArrayList<IntraDayNifty>();
		try {
			Date startDate = DateUtil.addDaysToDate(-1);
			Date endDate = DateUtil.getCurretDate();
			if (search.getStartDate() != null) {
				startDate = DateUtil.getDateWithoutTime(search.getStartDate());
				if(search.getEndDate() !=null)
					endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			}
			List<Date> dates = intraDayNiftyEquityRepository.getDistinctDateBetweenRange(startDate, endDate,search.getExpiryDate());
			for (Date date : dates) {
				finalEquity.addAll(getEquitiesByDates(date, date, search));
			}
		} catch (Exception e) {
			logger.error("IntraDayEquityService : Error",e);
		}
		
		return finalEquity;
	}

	
	public List<IntraDayNifty> getIntraDayYesterdayMinusToday(SearchFilter search) {
		Date startDate = search.getStartDate();
		Date endDate = search.getEndDate();

		try {
			List<IntraDayNifty> search1 = (List<IntraDayNifty>) intraDayNiftyEquityRepository.findAll();
			List<Date> dates = intraDayNiftyEquityRepository.getDistinctDateBetweenRange(startDate, endDate,search.getExpiryDate());
			List<IntraDayNifty> searchedEquity = new ArrayList<IntraDayNifty>();
			if(!dates.isEmpty()) {
				Date lastDate = dates.get(dates.size()-1);
				List<IntraDayNifty> thisEqty = getEquitiesByDates(dates.get(0), dates.get(0), search);
				List<IntraDayNifty> prevEqty = getEquitiesByDates(lastDate, lastDate, search);
				thisEqty.stream().forEach(thseq -> {

					Optional<IntraDayNifty> eqty = prevEqty.stream()
							.filter(pre -> pre.getStrikePrice() == thseq.getStrikePrice()).findFirst();
					if (eqty.isPresent()) {
						IntraDayNifty callDiff = eqty.get();
						callDiff.setOi(thseq.getOi() - eqty.get().getOi());
						callDiff.setChnginDif(thseq.getChnginOI() - eqty.get().getChnginOI());
						callDiff.setIv(thseq.getIv() - eqty.get().getIv());
						callDiff.setLtp(thseq.getLtp() - eqty.get().getLtp());
						callDiff.setVolumeDif(thseq.getVolume() - eqty.get().getVolume());
						callDiff.setPostionsVol(callDiff.getChnginDif()/callDiff.getVolumeDif());
						IntraDayNifty putDiff = eqty.get().getPut();
						putDiff.setOi(thseq.getPut().getOi() - eqty.get().getOi());
						putDiff.setChnginDif(thseq.getPut().getChnginOI() - eqty.get().getChnginOI());
						putDiff.setIv(thseq.getPut().getIv() - eqty.get().getIv());
						putDiff.setLtp(thseq.getPut().getLtp() - eqty.get().getLtp());
						putDiff.setVolumeDif(thseq.getPut().getVolume() - eqty.get().getVolume());
						putDiff.setPostionsVol(putDiff.getChnginDif()/putDiff.getVolumeDif());
						putDiff.setPrevEquity(null);
						thseq.getPut().setPrevEquity(putDiff);
						thseq.setPrevEquity(callDiff);
					}
				});

				searchedEquity.addAll(thisEqty);
			}
			
			/*for (Date date : dates) {
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
			}*/
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
			List<Date> dates = intraDayNiftyEquityRepository.getDistinctDateBetweenRange(startDate, endDate,search.getExpiryDate());
			List<IntraDayNifty> searchedEquity = new ArrayList<IntraDayNifty>();
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
	
	private List<IntraDayNifty> getEquitiesByDates(Date startDate, Date endDate, SearchFilter search) throws Exception{
		List<Filter> filters = search.getFilter();

		List<IntraDayNifty> finalEquity = new ArrayList<IntraDayNifty>();
		if (search.getStrikePrice() > 0) {
			List<IntraDayNifty> equities = intraDayNiftyEquityRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.PUT.getColumn());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<IntraDayNifty> equities = intraDayNiftyEquityRepository.getAllEquitiesBetweenDates(startDate,
				endDate, Column.PUT.getColumn(), search.getSymbol(),search.getExpiryDate());

		for (Filter filt : filters) {
			switch (filt.getKey()) {
			case "OI":
				finalEquity
						.addAll(equities.stream().sorted(Comparator.comparing(IntraDayNifty::getOi).reversed())
								.limit(search.getRecordCount()).collect(Collectors.toList()));
				break;
			case "CHANGE_IN_OI":
				List<IntraDayNifty> chngEquities = getChninOISort(equities);
				List<IntraDayNifty> sortedList = chngEquities.stream()
						.sorted(Comparator.comparing(IntraDayNifty::getChnginOI).reversed())
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

		return finalEquity.stream().filter(EquityDerivativePredicate.distinctByKeys(IntraDayNifty::getRowNo,
				IntraDayNifty::getDate)).collect(Collectors.toList());
	}
	
	private List<IntraDayNifty> getChninOISort(List<IntraDayNifty> equities) {

		List<IntraDayNifty> list = new ArrayList<IntraDayNifty>();
		equities.forEach(s -> {
			IntraDayNifty e = s;
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
