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
import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.enums.Column;
import com.example.stock.repo.NiftyEquityDerivativeRepository;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NiftyEquityServiceImpl implements NiftyEquityService {

	private String DIR = "D:\\Equity Data";

	@Autowired
	private ConfigService configService;

	private final static Logger logger = LoggerFactory.getLogger(StockOptionsEquityService.class);

	@Autowired
	private NiftyEquityDerivativeRepository equityDerivativeRepository;

	@Override
	public void saveNiftyEquityDerivatives() {

		List<String> dates = EquityDerivativesUtil.getExpiryDate(null);
		for (String date : dates) {
			String url = Constant.EQUITY_CHART_URL + "&date=" + date;
			List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(url);
			List<NiftyEquityDerivative> call = new ArrayList<NiftyEquityDerivative>();
			List<NiftyEquityDerivative> put = new ArrayList<NiftyEquityDerivative>();
			List<NiftyEquityDerivative> equities = new ArrayList<NiftyEquityDerivative>();
			resultObj.forEach(s -> {
				ObjectMapper mapper = new ObjectMapper();
				NiftyEquityDerivative equity = new NiftyEquityDerivative();
				equity = mapper.convertValue(s, NiftyEquityDerivative.class);
				equity.setDate(DateUtil.getDateWithoutTime(DateUtil.getCurretDate()));
				equity.setExpiryDate(date);
				equity.setId(null);
				equity.setPostionsVol(equity.getChnginOI() / equity.getVolume());
				if(equity.getType()==Column.CALL.getColumn())
					call.add(equity);
				if(equity.getType()==Column.PUT.getColumn())
					put.add(equity);
				
				equities.add(equity);
			});
			String sourceDir = (String) configService.getConfigByName(Constant.NIFTY_SOURCE_DIR);
			FileUtil.saveNiftyOptionsEquityAsJsonFile(equities, sourceDir,date);
			List<NiftyEquityDerivative> callDB = (List<NiftyEquityDerivative>) equityDerivativeRepository.saveAll(call);
			put.stream().forEach(putEq->{
				Optional<NiftyEquityDerivative> eqty = callDB.stream()
						.filter(pre -> pre.getRowNo() == putEq.getRowNo()).findFirst();
				if (eqty.isPresent()) {
					putEq.setPutId(eqty.get().getId());
				}
			});
			equityDerivativeRepository.saveAll(put);
		}
	}

	@Override
	public List<NiftyEquityDerivative> serachNiftyEquity(SearchFilter search) {

		List<NiftyEquityDerivative> finalEquity = new ArrayList<NiftyEquityDerivative>();
		try {
			Date startDate = DateUtil.addDaysToDate(-1);
			Date endDate = DateUtil.getCurretDate();
			if (search.getStartDate() != null) {
				startDate = DateUtil.getDateWithoutTime(search.getStartDate());
				if(search.getEndDate() !=null)
					endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			}
			List<NiftyEquityDerivative> fina =(List<NiftyEquityDerivative>) equityDerivativeRepository.findAll();
			List<Date> dates = equityDerivativeRepository.getDistinctDateBetweenRange(startDate, endDate,search.getExpiryDate());
			for (Date date : dates) {
				finalEquity.addAll(getEquitiesByDates(date, date, search));
			}
		} catch (Exception e) {
			logger.error("EquityService : Error",e);
		}
		
		return finalEquity;
	}

	@Override
	public List<NiftyEquityDerivative> getYesterdayMinusTodayK(SearchFilter search) {
		Date startDate = DateUtil.addDaysToDate(-search.getDays());
		Date endDate = DateUtil.getCurretDate();
		if (search.getStartDate() != null) {
			startDate = DateUtil.getDateWithoutTime(search.getStartDate());
			if (search.getEndDate() != null)
				endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			else
				endDate = DateUtil.getCurretDate();
		}

		try {
			List<Date> dates = equityDerivativeRepository.getDistinctDateBetweenRange(startDate, endDate,search.getExpiryDate());
			List<NiftyEquityDerivative> searchedEquity = new ArrayList<NiftyEquityDerivative>();
			for (Date date : dates) {
				Date thisDate = date;
				Date prevDate = DateUtil.getDateWithoutTime(DateUtil.addDaysToDate(date, -1));
				List<NiftyEquityDerivative> thisEqty = getEquitiesByDates(thisDate, thisDate, search);
				List<NiftyEquityDerivative> prevEqty = getEquitiesByDates(prevDate, DateUtil.getEndOfDay(prevDate), search);
				thisEqty.stream().forEach(thseq -> {

					Optional<NiftyEquityDerivative> eqty = prevEqty.stream()
							.filter(pre -> pre.getStrikePrice() == thseq.getStrikePrice()).findFirst();
					if (eqty.isPresent()) {
						NiftyEquityDerivative callDiff = eqty.get();
						callDiff.setOi(thseq.getOi() - eqty.get().getOi());
						callDiff.setChnginDif(thseq.getChnginOI() - eqty.get().getChnginOI());
						callDiff.setIv(thseq.getIv() - eqty.get().getIv());
						callDiff.setLtp(thseq.getLtp() - eqty.get().getLtp());
						callDiff.setVolumeDif(thseq.getVolume() - eqty.get().getVolume());
						callDiff.setPostionsVol(callDiff.getChnginDif()/callDiff.getVolumeDif());
						
						NiftyEquityDerivative putDiff = eqty.get().getPut();
						putDiff.setOi(thseq.getPut().getOi() - eqty.get().getOi());
						putDiff.setChnginDif(thseq.getPut().getChnginOI() - eqty.get().getChnginOI());
						putDiff.setIv(thseq.getPut().getIv() - eqty.get().getIv());
						putDiff.setLtp(thseq.getPut().getLtp() - eqty.get().getLtp());
						putDiff.setVolumeDif(thseq.getPut().getVolume() - eqty.get().getVolume());
						putDiff.setPostionsVol(putDiff.getChnginDif()/putDiff.getVolumeDif());
						thseq.getPut().setPrevEquity(putDiff);
						thseq.setPrevEquity(callDiff);
					}
				});

				searchedEquity.addAll(thisEqty);
			}
			return searchedEquity;
		} catch (Exception e) {
			logger.error("NiftyEquityServiceImpl: Error",e);
		}
		return null;
	}

	private List<NiftyEquityDerivative> getEquitiesByDates(Date startDate, Date endDate, SearchFilter search) throws Exception{
		List<Filter> filters = search.getFilter();

		List<NiftyEquityDerivative> finalEquity = new ArrayList<NiftyEquityDerivative>();
		if (search.getStrikePrice() > 0) {
			List<NiftyEquityDerivative> equities = equityDerivativeRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.valueOf(search.getType()).getColumn());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<NiftyEquityDerivative> equities = equityDerivativeRepository.getAllEquitiesBetweenDates(startDate,
				endDate, Column.PUT.getColumn(), search.getSymbol(),search.getExpiryDate());

		for (Filter filt : filters) {
			switch (filt.getKey()) {
			case "OI":
				finalEquity
						.addAll(equities.stream().sorted(Comparator.comparing(NiftyEquityDerivative::getOi).reversed())
								.limit(search.getRecordCount()).collect(Collectors.toList()));
				break;
			case "CHANGE_IN_OI":
				List<NiftyEquityDerivative> chngEquities = getChninOISort(equities);
				List<NiftyEquityDerivative> sortedList = chngEquities.stream()
						.sorted(Comparator.comparing(NiftyEquityDerivative::getChnginOI).reversed())
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

		return finalEquity.stream().filter(EquityDerivativePredicate.distinctByKeys(NiftyEquityDerivative::getRowNo,
				NiftyEquityDerivative::getDate)).collect(Collectors.toList());
	}

	// For sorting to skip negative value.
	// If negative value's no of digits is greater, then the value should be
	// consider
	// in sorting.
	private List<NiftyEquityDerivative> getChninOISort(List<NiftyEquityDerivative> equities) {

		List<NiftyEquityDerivative> list = new ArrayList<NiftyEquityDerivative>();
		equities.forEach(s -> {
			NiftyEquityDerivative e = s;
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
