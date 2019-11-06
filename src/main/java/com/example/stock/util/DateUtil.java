package com.example.stock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static Date getCurretDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeZone(TimeZone.getDefault());
        return cal.getTime();
	}
	
	public static Date addDaysToDate(int days) {
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, days);
		cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	public static Date addDaysToDate(Date date,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    cal.add(Calendar.DATE, days);
		cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	public static Date addMinutesToDate(Date date,int min) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, date.getHours());
		if(min<0) {
			min = -1*min;
			cal.set(Calendar.MINUTE, date.getMinutes()-min);
		}else
			cal.set(Calendar.MINUTE, date.getMinutes()+min);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
	}
	
	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	public static Date getDateWithoutSec(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	public static String getDateAsString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh-mm-ss");
		String dateStr = dateFormat.format(cal.getTime());
		return dateStr;
	}
	
	public static Date stringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date dateStr=getCurretDate();
		try {
			dateStr = dateFormat.parse(date);
		} catch (ParseException e) {
			System.out.println(e);
		}
		return getDateWithoutTime(dateStr);
	}
	
	public static Date setDateWithTime(int hr,int min) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hr);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	public static Date formateAsYYMMDDHH(Date date) {
		try { 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String dateStr = dateFormat.format(date);
		 SimpleDateFormat dateFrm = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			try {
				date = dateFrm.parse(dateStr);
				cal.setTime(date);
			} catch (ParseException e) {
				System.out.println(e);
			}
		return date;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return date;
		
	}
	
	public static Date getEndOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
}
