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
import com.example.stock.bean.StockOptionsEquity;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.dto.SearchFilter;
import com.example.stock.enums.Column;
import com.example.stock.repo.NiftyEquityDerivativeRepository;
import com.example.stock.repo.StockOptionsEquityRepository;
import com.example.stock.util.DateUtil;
import com.example.stock.util.EquityDerivativesUtil;
import com.example.stock.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NiftyEquityServiceImpl implements NiftyEquityService {

	private String DIR = "D:\\Equity Data";

	@Autowired
	private ConfigService configService;

	private final static Logger logger = LoggerFactory.getLogger(StockOptionsEquityLookupService.class);

	@Autowired
	private NiftyEquityDerivativeRepository equityDerivativeRepository;

	@Autowired
	private StockOptionsEquityRepository stockOptionsEquityRepository;
	
	@Autowired
	private StockOptionsEquityLookupService equityLookupService ;

	@Override
	public void saveNiftyEquityDerivatives() {
		List<Map<String, Double>> resultObj = EquityDerivativesUtil.getEquityData(Constant.EQUITY_CHART_URL);
		List<NiftyEquityDerivative> equities = new ArrayList<NiftyEquityDerivative>();
		resultObj.forEach(s -> {
			ObjectMapper mapper = new ObjectMapper();
			NiftyEquityDerivative equity = new NiftyEquityDerivative();
			equity = mapper.convertValue(s, NiftyEquityDerivative.class);
			equity.setDate(DateUtil.getCurretDate());
			equity.setId(null);
			equity.setPostionsVol(equity.getChnginOI() / equity.getVolume());
			equities.add(equity);
		});
		String sourceDir = (String) configService.getConfigByName(Constant.NIFTY_SOURCE_DIR);
		FileUtil.saveNiftyOptionsEquityAsJsonFile(equities, sourceDir);
		equityDerivativeRepository.saveAll(equities);
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
			List<Date> dates = equityDerivativeRepository.getDistinctDateBetweenRange(startDate, endDate);
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
			List<Date> dates = equityDerivativeRepository.getDistinctDateBetweenRange(startDate, endDate);
			List<NiftyEquityDerivative> searchedEquity = new ArrayList<NiftyEquityDerivative>();
			for (Date date : dates) {
				Date thisDate = date;
				Date prevDate = DateUtil.addDaysToDate(date, -1);
				List<NiftyEquityDerivative> thisEqty = getEquitiesByDates(thisDate, thisDate, search);
				List<NiftyEquityDerivative> prevEqty = getEquitiesByDates(prevDate, prevDate, search);
				thisEqty.stream().forEach(thseq -> {

					Optional<NiftyEquityDerivative> eqty = prevEqty.stream()
							.filter(pre -> pre.getStrikePrice() == thseq.getStrikePrice()).findFirst();
					if (eqty.isPresent()) {
						NiftyEquityDerivative diffEqty = eqty.get();
						diffEqty.setOi(thseq.getOi() - eqty.get().getOi());
						diffEqty.setChnginDif(thseq.getChnginOI() - eqty.get().getChnginOI());
						diffEqty.setIv(thseq.getIv() - eqty.get().getIv());
						diffEqty.setLtp(thseq.getLtp() - eqty.get().getLtp());
						diffEqty.setVolumeDif(thseq.getVolume() - eqty.get().getVolume());
						diffEqty.setPostionsVol(diffEqty.getChnginDif()/diffEqty.getVolume());
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

	private List<NiftyEquityDerivative> getEquitiesByDates(Date startDate, Date endDate, SearchFilter search) throws Exception{
		List<Filter> filters = search.getFilter();

		List<NiftyEquityDerivative> finalEquity = new ArrayList<NiftyEquityDerivative>();
		if (search.getStrikePrice() > 0) {
			List<NiftyEquityDerivative> equities = equityDerivativeRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.valueOf(search.getType()).ordinal());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<NiftyEquityDerivative> equities = equityDerivativeRepository.getAllEquitiesBetweenDatesAndByType(startDate,
				endDate, Column.valueOf(search.getType()).ordinal(), search.getSymbol());

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

	
	// STOCK OPTION CHAIN CODE STARTS.................!
	@Override
	public void saveStockOptionsEquity() {
		try {
			equityLookupService.loadStocksOptionsData();
		} catch (InterruptedException e) {
		logger.error("Error while saving stocks option data");
		}
	}
	
	@Override
	public List<StockOptionsEquity> serachStocksOptionEquity(SearchFilter search) {
		List<StockOptionsEquity> stockOptionsList = new ArrayList<StockOptionsEquity>();
		try {
			Date startDate = DateUtil.addDaysToDate(-1);
			Date endDate = DateUtil.getCurretDate();
			if (search.getStartDate() != null) {
				startDate = DateUtil.getDateWithoutTime(search.getStartDate());
				if(search.getEndDate() !=null)
					endDate = DateUtil.getDateWithoutTime(search.getEndDate());
			}
			List<Date> dates = stockOptionsEquityRepository.getDistinctDateBetweenRange(startDate, endDate);
			for (Date date : dates) {
				stockOptionsList.addAll(getStockEquitiesByDates(date, date, search));
			}
		} catch (Exception e) {
			logger.error("EquityService : Error",e);
		}
		
		return stockOptionsList;
	}

		
	private List<StockOptionsEquity> getStockEquitiesByDates(Date startDate, Date endDate, SearchFilter search) {
		List<Filter> filters = search.getFilter();

		List<StockOptionsEquity> finalEquity = new ArrayList<StockOptionsEquity>();
		if (search.getStrikePrice() > 0) {
			List<StockOptionsEquity> equities = stockOptionsEquityRepository
					.getEquitiesByStrikePriceBetweenDatesAndByType(startDate, endDate, search.getStrikePrice(),
							Column.valueOf(search.getType()).ordinal());
			return equities;
		}
		if (search.getSymbol() == null) {
			search.setSymbol("");
		}
		List<StockOptionsEquity> equities = stockOptionsEquityRepository.getAllEquitiesBetweenDatesAndByType(startDate,
				endDate, Column.valueOf(search.getType()).ordinal(), search.getSymbol());

		for (Filter filt : filters) {
			switch (filt.getKey()) {
			case "OI":
				finalEquity
						.addAll(equities.stream().sorted(Comparator.comparing(StockOptionsEquity::getOi).reversed())
								.limit(search.getRecordCount()).collect(Collectors.toList()));
				break;
			case "CHANGE_IN_OI":
				List<StockOptionsEquity> chngEquities = getStocksChninOISort(equities);
				List<StockOptionsEquity> sortedList = chngEquities.stream()
						.sorted(Comparator.comparing(StockOptionsEquity::getChnginOI).reversed())
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

		return finalEquity.stream().filter(EquityDerivativePredicate.distinctByKeys(StockOptionsEquity::getRowNo,
				StockOptionsEquity::getDate)).collect(Collectors.toList());
	}
	
	// For sorting to skip negative value.
		// If negative value's no of digits is greater, then the value should be
		// consider
		// in sorting.
		private List<StockOptionsEquity> getStocksChninOISort(List<StockOptionsEquity> equities) {

			List<StockOptionsEquity> list = new ArrayList<StockOptionsEquity>();
			equities.forEach(s -> {
				StockOptionsEquity e = s;
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
