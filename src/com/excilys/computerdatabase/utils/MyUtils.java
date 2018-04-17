package com.excilys.computerdatabase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {
	
	
	/* Convertie une date en format yyyy-MM-dd
	 * @param date
	 * @return String "yyyy-MM-dd"
	 */
	public static String formatDateToString(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	
	/* Convertie une date en sql.date tout en passant au format yyyy-MM-dd
	 * @param date
	 * @return sql.date format "yyyy-MM-dd"
	 */
	public static java.sql.Date formatDateUtilToSQLDate(Date date)
	{
		if(date != null)
			return java.sql.Date.valueOf(formatDateToString(date));
		else
			return null;
	}
	
	
	/* Convertie un string avec le format yyyy-MM-dd ou renvoie null si sDate est null
	 * @param sDate "yyyy-MM-dd" or NULL
	 * @return une date
	 * @throw cas ou le format du string n'est pas valide
	 */
	public static Date stringToDate(String sDate) throws ParseException
	{
		if(!sDate.equals("null") && sDate != null)
		{
			SimpleDateFormat format;
			format = new SimpleDateFormat("yyyy-MM-dd");
			return format.parse(sDate);
		}
		else
			return null;
	}

}
