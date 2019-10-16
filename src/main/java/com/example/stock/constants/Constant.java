package com.example.stock.constants;

public class Constant {

	public static final String PUT = "Put-";
	public static final String CALL = "Call-"; 
	public static final String COLMN_TYPE="type";
	
	public static final Integer COLUMN_PUT = 0;
	public static final Integer COLUMN_CALL = 1;
	public static final String ROW = "rowNo";
	
	//Triggers constants
	public static final String TRIGGER_RANGE = "triggerRange";
	public static final String START = "start";
	public static final String END = "end";
	public static final String TRIGGER_LAST_VALUE = "triggerLastValue";
	
	//Directory constants
	//public static final String EQUITY_SOURCE_DIR = "equitySourceDir";
	//public static final String STOCK_SOURCE_DIR = "stockSourceDir";
	//public static final String ACTIVITY_SOURCE_DIR = "activitySourceDir";
	//public static final String STOCK_EQUITY_SOURCE_DIR ="stockEquitySourceDir";
	
	
	public static final String NIFTY_SOURCE_DIR = "equitySourceDir";
	public static final String STOCK_SOURCE_DIR = "stockSourceDir";
	public static final String FII_SOURCE_DIR = "activitySourceDir";
	public static final String STOCK_OPTIONS_SOURCE_DIR ="stockEquitySourceDir";
	public static final String INTRADAY_NIFTY_SOURCE_DIR = "intraDayEquitySourceDir";
	public static final String MONTHLY_NIFTY_SOURCE_DIR = "monthlyEquitySourceDir";
	public static final String NIFTY_PRIMIUMDK_SOURCE_DIR = "niftyPremiumDKSourceDir";
	
	public static String EQUITY_CHART_URL = "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbol=NIFTY"; 
}
