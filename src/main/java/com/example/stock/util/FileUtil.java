package com.example.stock.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.stock.bean.IntraDayNifty;
import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.StockOptionsEquity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUtil {

	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static String getDriveName() {
		String driveName = "D:/";
		File[] drives = File.listRoots();
		if (drives != null && drives.length > 0) {
			driveName = drives[0].getPath();
		}
		return driveName;
	}

	public static String createDir(String dirPathName) {
		String dir = dirPathName;
		if (!isExist(dirPathName)) {
			dir = getDriveName() + dirPathName;
			if (dirPathName.contains(getDriveName())) {
				dir = dirPathName;
			}
			new File(dir).mkdirs();
		} else {
			dir = dirPathName;
		}
		return dir;
	}

	public static boolean isExist(String dirPathName) {
		File dir = new File(dirPathName);
		return dir.exists();
	}

	/*
	 * public static void main(String[] args) { createDir("Equity\\data"); }
	 */

	public static File[] getAllFiles(String directory, String fileFormat) {
		File[] files = new File(directory).listFiles(new FileFilter() {
			@Override
			public boolean accept(File path) {
				if (path.isFile() && path.getName().contains(fileFormat)) {
					return true;
				}
				return false;
			}
		});
		return files;
	}

	public static void saveNiftyOptionsEquityAsJsonFile(List<NiftyEquityDerivative> equities, String sourcePath,String... filePrefix) {
		try {
			ObjectMapper Obj = new ObjectMapper();
			String jsonStr = Obj.writeValueAsString(equities);
			String fileName = filePrefix[0]+"-"+DateUtil.getDateAsString() + ".json";

			FileWriter file = new FileWriter(sourcePath + "\\" + fileName);
			file.write(jsonStr);
			file.close();
			logger.info("New file " + fileName + "is created");
		} catch (IOException e) {
		}
	}
	
	public static void saveStockOptionsEquityAsJsonFile(List<StockOptionsEquity> equities, String sourcePath) {
		try {
			ObjectMapper Obj = new ObjectMapper();
			String jsonStr = Obj.writeValueAsString(equities);
			String fileName = DateUtil.getDateAsString() + ".json";

			FileWriter file = new FileWriter(sourcePath + "\\" + fileName);
			file.write(jsonStr);
			file.close();
			logger.info("New file " + fileName + "is created");
		} catch (IOException e) {
		}
	}
	
	public static void saveAsJsonFile(Object listObj, String sourcePath) {
		try {
			ObjectMapper Obj = new ObjectMapper();
			String jsonStr = Obj.writeValueAsString(listObj);
			String fileName = DateUtil.getDateAsString() + ".json";

			FileWriter file = new FileWriter(sourcePath + "\\" + fileName);
			file.write(jsonStr);
			file.close();
			logger.info("New file " + fileName + "is created");
		} catch (IOException e) {
		}
	}
	
	public static void saveAsCSVFile(List<Map<String, Double>> listObj, String sourcePath) {
		try {
			FileWriter writer;
			String fileName = sourcePath + "\\" +DateUtil.getDateAsString() + ".csv";
			writer = new FileWriter(fileName, true);  //True = Append to file, false = Overwrite
			
			// Write CSV
			for (int i = 0; i < listObj.size(); i++) {	 
				writer.write(listObj.get(i).get("symbol").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("oi").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("chnginOI").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("volume").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("iv").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("ltp").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("netChng").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("bidQty").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("bidPrice").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("askPrice").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("askQty").toString());
				writer.write(",");
				writer.write(listObj.get(i).get("strikePrice").toString());
				writer.write("\r\n");
			}
			
			writer.close();
			logger.info("New file " + fileName + "is created");
		} catch (IOException e) {
		}
	}
}
