package com.example.stock.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock.bean.ExchangeActivity;
import com.example.stock.bean.Stock;
import com.example.stock.bean.StockPredicate;
import com.example.stock.constants.Constant;
import com.example.stock.dto.Filter;
import com.example.stock.enums.Direction;
import com.example.stock.repo.ExchangeActivityRepository;
import com.example.stock.repo.StockRepository;
import com.example.stock.util.CSVReader;
import com.example.stock.util.FileUtil;

@Service
public class StockServiceImpl implements StockService {

	private String DIR = "D:\\Equity Data";

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private ConfigService configService;
	
	private final static Logger logger = LoggerFactory.getLogger(StockOptionsEquityLookupService.class);
	
	@Autowired
	private ExchangeActivityRepository activityRepository;

	private List<Stock> thisDateStock = Arrays.asList();

	@Override
	public List<Stock> getAllStocks() {
		return (List<Stock>) stockRepository.findAll();
	}

	@Override
	public void saveStocks(String sourceDir) {
		sourceDir =  (String) configService.getConfigByName(Constant.STOCK_SOURCE_DIR);
	}

	@Override
	public List<Stock> getStocks(Date startDate, Date endDate) {
		return stockRepository.getAllStocksBetweenDates(startDate, endDate);
	}

	@Override
	public List<Stock> findHoldingStocks(Date startDate, Date endDate, List<Filter> filters) {
		List<Stock> finalStock = new ArrayList<Stock>();

		List<Date> dates = stockRepository.getDistinctDateBetweenRange(startDate, endDate);
		startDate = dates.get(0);
		endDate = dates.get(dates.size() - 1);
		thisDateStock = stockRepository.getAllStocksBetweenDates(startDate, startDate);

		for (Date date : dates) {
			
			finalStock = getGreateStock(thisDateStock, date, filters);
			if (finalStock.size() > 0 && thisDateStock.size() == 0) {
				break;
			}
			startDate = new Date(startDate.getTime() + (1000 * 60 * 60 * 24));
		}
		return finalStock;
	}

	private List<Stock> getGreateStock(List<Stock> startStock, Date date, List<Filter> filters) {
		List<Stock> finalStock = new ArrayList<Stock>();

		List<Stock> stockNext = stockRepository.getAllStocksBetweenDates(date, date);

		startStock.forEach(start -> {
			Optional<Stock> stock = stockNext.stream().filter(s -> s.getName().equalsIgnoreCase(start.getName()))
					.findFirst();

			if (stock.isPresent()) {
				/*
				 * if(stock.get().getClosePrice()>start.getClosePrice()) {
				 * finalStock.add(stock.get()); }
				 */
				Boolean isMatch = false;
				for (Filter filt : filters) {
					switch (filt.getKey()) {

					case "CLOSE_PRICE":
						if (Direction.UP.name().equalsIgnoreCase(filt.getDirection()))
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isClosePriceGreater(start.getClosePrice()));
						else
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isClosePriceLess(start.getClosePrice()));
						break;
					case "TRADE_QTY":
						if (Direction.UP.name().equalsIgnoreCase(filt.getDirection()))
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isTtlTradeQtyGreater(start.getTtlTradeQty()));
						else
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isTtlTradeQtyLess(start.getTtlTradeQty()));
						break;
					case "DELIV_QTY":
						if (Direction.UP.name().equalsIgnoreCase(filt.getDirection()))
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isDeliverQtyGreater(start.getClosePrice()));
						else
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isDeliverQtyLess(start.getTtlTradeQty()));
						break;
					case "DELIV_PER":
						if (Direction.UP.name().equalsIgnoreCase(filt.getDirection()))
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isDeliverPerGreater(start.getTtlTradeQty()));
						else
							isMatch = StockPredicate.filterAndGet(stock.get(),
									StockPredicate.isDeliverPerLess(start.getTtlTradeQty()));
						break;
					default:
						break;
					}
					if (isMatch)
						continue;
					else
						break;
				}
				if (isMatch || filters.isEmpty()) {
					finalStock.add(stock.get());
				}
				// boolean st = StockPredicate.filterAndGet(stock.get(),
				// StockPredicate.isClosePriceGreater(start.getClosePrice()));

			}

		});
		thisDateStock = stockNext;
		return finalStock;

	}

	List<Stock> process(List<Stock> stocks, Predicate<Stock> predicate) {
		List<Stock> result = new ArrayList<>();
		for (Stock stock : stocks) {
			if (predicate.test(stock)) {
				result.add(stock);
			}
		}
		return result;
	}

	@Override
	public void loadActivityData() throws Exception {
		String dir = (String) configService.getConfigByName(Constant.FII_SOURCE_DIR);
		File[] files = FileUtil.getAllFiles(dir, ".csv");
		for (int i = 0; i < files.length; i++) {
			List<ExchangeActivity> exchangeActivity = CSVReader.writeCsvToActivity(files[i].getAbsolutePath());
			activityRepository.saveAll(exchangeActivity);
		}
		}
	
	
	@Override
	public List<ExchangeActivity> getActivityData(Date startDate,Date endDate) throws Exception {
		return (List<ExchangeActivity>) activityRepository.findAll();
	}
}
