package com.excilys.computerdatabase.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MyUtils {
	
	private static Scanner scanner = new Scanner(System.in);
	
	/** Convertie une date en format dd-MM-yyyy
	 * @param date
	 * @return String "dd-MM-yyyy"
	 */
	public static String formatDateToString(LocalDate date)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(formatter);
	}
	
	
	/** Convertie une date en sql.date
	 * @param date LocalDate
	 * @return sql.date
	 */
	public static java.sql.Date formatDateUtilToSQLDate(LocalDate date)
	{
		if(date != null)
			return java.sql.Date.valueOf(date);
		else
			return null;
	}
	
	
	/** Convertie un string en LocalDate ou renvoie null si sDate est null
	 * @param sDate String for a date
	 * @return date 
	 * @throw this string didn't work
	 */
	public static LocalDate stringToDate(String sDate) throws ParseException
	{
		if(!sDate.equals("null") && sDate != null)
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			return LocalDate.parse(sDate, dtf);
		}
		else
			return null;
	}
	
	
	/** boucle qui demande à l'utilisateur une date valide à l'utilisateur
	 * sous format dd-MM-yyyy
	 * 
	 * @return une date valide
	 */
	public static LocalDate requestOkDate()
	{
		LocalDate date = null;
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
