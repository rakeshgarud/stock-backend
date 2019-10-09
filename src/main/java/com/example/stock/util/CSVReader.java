package com.example.stock.util;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.example.stock.bean.ExchangeActivity;
import com.example.stock.bean.Stock;

public class CSVReader {
	

	//static List<String> stockNames = Arrays.asList("20MICRONS","ADANIGAS","TATACOFFEE");
	
	public static List<Stock> writeCsvToStock(String fileName)  {

		List<Stock> stocks = new ArrayList<Stock>();
		// Create the CSVFormat object
		CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

		// initialize the CSVParser object
		CSVParser parser = null;
		try {
			parser = new CSVParser(new FileReader(fileName), format);
			for (CSVRecord record : parser) {
				//if(stockNames.contains(record.get("SYMBOL"))) {
					Stock stock = new Stock();
					stock.setName(record.get("SYMBOL"));
					stock.setDate(new SimpleDateFormat("dd-MMM-yyyy").parse(record.get(" DATE1").toString()));
					stock.setPrevClose(Double.parseDouble(record.get(" PREV_CLOSE")));
					stock.setClosePrice(Double.parseDouble(record.get(" CLOSE_PRICE")));
					if(record.get(" TTL_TRD_QNTY").trim().matches("[0-9]+"))
						stock.setTtlTradeQty(Double.parseDouble(record.get(" TTL_TRD_QNTY")));
					if(record.get(" DELIV_QTY").trim().matches("[0-9]+"))
						stock.setDeliveryQty(Double.parseDouble(record.get(" DELIV_QTY")));
					if(record.get(" DELIV_PER").trim().matches("[0-9]+(\\.[0-9][0-9]?)?+"))
						stock.setDeliveryPer(Double.parseDouble(record.get(" DELIV_PER")));
					stocks.add(stock);
				//}
			}
			// close the parser
			parser.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stocks;
	}
	
	public static List<ExchangeActivity> writeCsvToActivity(String fileName)  {

		List<ExchangeActivity> activities = new ArrayList<ExchangeActivity>();
		// Create the CSVFormat object
		CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
		
		// initialize the CSVParser object
		CSVParser parser = null;
		try {
			parser = new CSVParser(new FileReader(fileName), format);
			for (CSVRecord record : parser) {
				if(record.get("Client Type").toString().equals("FII")) {
					ExchangeActivity activity = new ExchangeActivity();
					activity.setClientType(record.get("Client Type"));
					activity.setDate(DateUtil.getCurretDate());
					activity.setFutureIdxLong(Double.parseDouble(record.get("Future Index Long")));
					activity.setFutureIdxShort(Double.parseDouble(record.get("Future Index Short")));
					activity.setFutureStockLong(Double.parseDouble(record.get("Future Stock Long")));
					activity.setFutureStockShort(Double.parseDouble(record.get("Future Stock Short	")));
					activity.setOptionIdxCallLong(Double.parseDouble(record.get("Option Index Call Long")));
					activity.setOptionIdxCallShort(Double.parseDouble(record.get("Option Index Call Short")));
					activity.setOptionIdxPutLong(Double.parseDouble(record.get("Option Index Put Long")));
					activity.setOptionIdxPutShort(Double.parseDouble(record.get("Option Index Put Short")));
					activity.setOptionStockCallLong(Double.parseDouble(record.get("Option Stock Call Long")));
					activity.setOptionStockCallShort(Double.parseDouble(record.get("Option Stock Call Short")));
					activity.setOptionStockPutLong(Double.parseDouble(record.get("Option Stock Put Long")));
					activity.setOptionStockPutShort(Double.parseDouble(record.get("Option Stock Put Short")));
					activity.setTotalLongContracts(Double.parseDouble(record.get("Total Long Contracts	")));
					activity.setTotalShortContracts(Double.parseDouble(record.get("Total Short Contracts")));
					activities.add(activity);
				}
			}
			// close the parser
			parser.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activities;
	}
	
	
}