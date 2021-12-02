package cc.grum.base.backendspringboot.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

	private final static String DEFAULT_DATETIME_FORMAT = "yyyyMMddHHmmss";
	private final static String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	// 변환 Date => String
	public static String date2String(Date date, String format){
		return new SimpleDateFormat(format).format(date);
	}
	// 변환 String => Date
	public static Date string2Date(String date ) throws ParseException {
		return string2Date(date, DEFAULT_DATETIME_FORMAT);
	}
	// 변환 String => Date
	public static Date string2Date(String dateString, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateString);
	}

	// 목적 : 현재 날짜를 yyyyMMddHHmmss 형식으로 출력한다.
	public static String now(){
		return date2String(new Date(), DEFAULT_DATETIME_FORMAT);
	}
	public static String today(){
		return today(DEFAULT_DATE_FORMAT);
	}
	public static String today(String format){
		if (StringUtils.isBlank(format)) format = DEFAULT_DATE_FORMAT;
		return date2String(new Date(), format);
	}
	public static String yesterday(){
		return yesterday(DEFAULT_DATE_FORMAT);
	}
	public static String yesterday(String format){
		if (StringUtils.isBlank(format)) format = DEFAULT_DATE_FORMAT;
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		Date today = new Date();
		Date tomorrow = new Date(today.getTime() - (1000 * 60 * 60 * 24));
		return dateformat.format(tomorrow);
	}
	public static String tomorrow(){
		return tomorrow(DEFAULT_DATE_FORMAT);
	}
	public static String tomorrow(String format){
		if (StringUtils.isBlank(format)) format = DEFAULT_DATE_FORMAT;
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		Date today = new Date();
		Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        return dateformat.format(tomorrow);
	}
}
