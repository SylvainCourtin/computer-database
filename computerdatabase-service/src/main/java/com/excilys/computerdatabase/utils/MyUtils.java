package com.excilys.computerdatabase.utils;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyUtils {
	
	private static Logger logger = LoggerFactory.getLogger(MyUtils.class);
	private static Scanner scanner = new Scanner(System.in);
	
	/** Convertie une date en format dd-MM-yyyy
	 * @param date
	 * @return String "dd-MM-yyyy"
	 */
	public static String formatDateToString(LocalDate date) throws DateTimeParseException
	{
		if(date != null)
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return date.format(formatter);
		}
		logger.error("The Localdate date can't be null !");
		return null;
		
	}
	
	
	/** Convertie une date en sql.date
	 * @param date LocalDate
	 * @return sql.date
	 */
	public static Date formatDateUtilToSQLDate(LocalDate date)
	{
		if(date != null)
		{
			date.atStartOfDay(ZoneId.of("Europe/Paris"));
			//TODO corriger le probleme de décalage de date à cause de la BDD
			return Date.valueOf(date.plusDays(1));
		}
		else
			return null;
	}
	
	/**
	 * Check if the style is - or / for the date writing like dd MM yyyy
	 * @param sDate
	 * @return
	 * @throws DateTimeParseException
	 */
	private static DateTimeFormatter knowFormat(String sDate) throws DateTimeParseException
	{
		if(sDate.contains("-"))
			return DateTimeFormatter.ofPattern("dd-MM-yyyy");
		else if(sDate.contains("/"))
		{
			return DateTimeFormatter.ofPattern("dd/MM/yyyy");
		}
		else
			throw new DateTimeException("Wrong format");
	}
	
	/**
	 * Check if the style is - or / for the date writing like yyyy MM dd
	 * @param sDate
	 * @return
	 * @throws DateTimeParseException
	 */
	private static DateTimeFormatter knowFormatInv(String sDate) throws DateTimeParseException
	{
		if(sDate.contains("-"))
			return DateTimeFormatter.ofPattern("yyyy-MM-dd");
		else if(sDate.contains("/"))
		{
			return DateTimeFormatter.ofPattern("yyyy/MM/dd");
		}
		else
			throw new DateTimeParseException("Wrong format : ", sDate, 0);
	}
	
	/** Convertie un string en LocalDate ou renvoie null si sDate est null
	 * @param sDate String for a date format : "dd-MM-yyyy"
	 * @return date 
	 * @throw this string didn't work
	 */
	public static LocalDate stringToDate(String sDate) throws DateTimeParseException
	{
		if(!sDate.equals("null") && sDate != null)
		{
			DateTimeFormatter dtf = knowFormat(sDate);
			return LocalDate.parse(sDate, dtf);
		}
		else
			return null;
	}
	
	/** Convertie un string en LocalDate ou renvoie null si sDate est null
	 * @param sDate String for a date format : "yyyy-MM-dd"
	 * @return date 
	 * @throw this string didn't work
	 */
	public static LocalDate stringToDateInv(String sDate) throws DateTimeParseException
	{
		if(!sDate.equals("null") && sDate != null)
		{
			DateTimeFormatter dtf = knowFormatInv(sDate);
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
			} catch (DateTimeParseException e) {
				System.out.println("Unvalidate format, try again or write null");
				isValid	= false;
			}
		}
		return date;
	}
	
	public static LocalDate plusDays(LocalDate date)
	{
		if(date != null)
			return date.plusDays(1);
		 return null;
	}
	public static LocalDate minusDays(LocalDate date)
	{
		if(date != null)
			return date.minusDays(1);
		return null;
	}

}
