package com.example.stock.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public static void saveNiftyOptionsEquityAsJsonFile(List<NiftyEquityDerivative> equities, String sourcePath) {
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
}
