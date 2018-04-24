package com.excilys.computerdatabase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MyUtils {
	
	private static Scanner scanner = new Scanner(System.in);
	
	/** Convertie une date en format yyyy-MM-dd
	 * @param date
	 * @return String "yyyy-MM-dd"
	 */
	public static String formatDateToString(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	/** Convertie une date en format dd-MM-yyyy, utiliser pour l'affichage
	 * @param date
	 * @return String "dd-MM-yyyy"
	 */
	public static String dateToString(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(date);
	}
	
	
	/** Convertie une date en sql.date tout en passant au format yyyy-MM-dd
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
	
	
	/** Convertie un string avec le format dd-MM-yyyy ou renvoie null si sDate est null
	 * @param sDate "dd-MM-yyyy" or NULL
	 * @return date 
	 * @throw this string didn't work
	 */
	public static Date stringToDate(String sDate) throws ParseException
	{
		if(!sDate.equals("null") && sDate != null)
		{
			SimpleDateFormat format;
			format = new SimpleDateFormat("dd-MM-yyyy");
			return format.parse(sDate);
		}
		else
			return null;
	}
	
	/** boucle qui demande à l'utilisateur une date valide à l'utilisateur
	 * sous format dd-MM-yyyy
	 * 
	 * @return une date valide
	 */
	public static Date RequestOkDate()
	{
		Date date = null;
		boolean isValid = false;
		while(!isValid)
		{
			try {
				date = stringToDate(scanner.nextLine().trim());
				isValid = true;
			} catch (ParseException e) {
				System.out.println("Unvalidate format, try again or write null");
				isValid	= false;
			}
		}
		return date;
	}


}
