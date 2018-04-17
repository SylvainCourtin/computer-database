package com.excilys.computerdatabase.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {
	
	
	/* Convertie une date en format yyyy-MM-dd
	 * @param date
	 * @return String "yyyy-MM-dd"
	 */
	public static String formatDateToSQL(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	public static java.sql.Date formatDateUtilToSQLDate(Date date)
	{
		return java.sql.Date.valueOf(formatDateToSQL(date));
	}

}
