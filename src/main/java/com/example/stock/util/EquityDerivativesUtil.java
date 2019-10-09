package com.example.stock.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import com.example.stock.constants.Constant;
import com.example.stock.enums.Column;

public class EquityDerivativesUtil {

	public static List<Map<String, Double>> getEquityData(String url) {
		if (url == null)
			url = "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbolCode=-10006&symbol=NIFTY&symbol=NIFTY&instrument=-&date=-&segmentLink=17&symbolCount=2&segmentLink=17";
		List<Map<String, Double>> resultObj = new ArrayList<Map<String, Double>>();
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Element table = doc.select("table#octable").get(0); // Selector for Table
			Elements rows = table.select("tr");

			Elements ths = rows.get(1).select("th");
			Map<Integer, String> header = new HashMap<Integer, String>();
			int c = 0;
			for (Element th : ths) {
				th.text().trim();
				String keys[] = th.text().trim().split(" ");
				String key;
				if (keys.length > 1) {
					key = keys[0].toLowerCase();
					for(int i =0;i<keys.length;i++)
						if(i>0)
							key +=keys[i];
				}
				else
					key = keys[0].toLowerCase();
				header.put(c, key);
				c++;
			}
			int rowCount = 0;
			String regex = "[+-]?[0-9][0-9]*";
			for (Element row : rows) {
				if (rowCount > 1) { // Skipping header rows
					Elements tds = row.select("td");

					Map<String, Double> callMap = new HashMap<String, Double>();
					Map<String, Double> putMap = new HashMap<String, Double>();

					callMap.put(Constant.COLMN_TYPE, (double) Column.CALL.ordinal());
					callMap.put(Constant.ROW, (double) rowCount);
					putMap.put(Constant.COLMN_TYPE, (double) Column.PUT.ordinal());
					putMap.put(Constant.ROW, (double) rowCount);

					int i = 0;
					for (Element td : tds) {
						double value = 0;
						String strVal = td.text().replace(",", "").trim();
						String key = StringUtils.uncapitalize(header.get(i).toString());
						try {
						value = Double.parseDouble(strVal);
						} catch (NumberFormatException e) {
							value = 0;
							if (strVal.startsWith("-") && strVal.length()>1) {
								strVal = strVal.replace("-", "");
								value = -Double.parseDouble(strVal);
							}
						}
						
						// Putting header as key by appending PUT- & Call- for differentiation
						if (i == 11) {
							callMap.put(key, value);
							putMap.put(key, value);
						}
						if (i <= 11) { // first 11 col of Call
							callMap.put(key, value);
						} else { // col of Put
							putMap.put(key, value);
						}
						i++;
					}
					// System.out.println(tds.text());
					resultObj.add(callMap);
					resultObj.add(putMap);
				}
				rowCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultObj.stream().limit(resultObj.size() - 2).collect(Collectors.toList());
	}
}
