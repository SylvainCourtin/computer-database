package com.excilys.computerdatabase.ui;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.page.PageComputer;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyUtils;

public class ViewComputer {
	
	private ServiceComputer serviceComputer;
	private Scanner scanner = new Scanner(System.in);
	
	public ViewComputer() {
		serviceComputer = new ServiceComputer();
	}
	
	public void showList()
	{
		PageComputer.getInstance(serviceComputer.getComputers()).menuPage();
	}
	//affichage d'un pc
	/*
	 * @param id computer id
	 */
	public void showOneComputer(long id)
	{
		if(serviceComputer.getComputer(id) != null)
			System.out.println(serviceComputer.getComputer(id).toStringDetails());
		else
			System.err.println("Cette ID n'existe pas !");
	}
	
	//demande un nom (pour la création d'un computer ou update)
	public String showRequestName()
	{
		return scanner.nextLine().trim();
	}
	
	//demande d'une date ou null (format exigeant !)
	public Date showRequestDateIntroduced()
	{
		return RequestOkDate();
	}
	//demande d'une date ou null (format exigeant !)
	public Date showRequestDateDiscontinued()
	{
		return RequestOkDate();
		
	}
	//effectue la création d'un computer
	public boolean createComputer(String name, Date dateIntroduced, Date dateDiscontinued, Company company)
	{
		try {
			return serviceComputer.addComputer(name, dateIntroduced, dateDiscontinued, company );
				
		} catch (DateDiscontinuedIntroducedException e) {
			System.out.println(e.getMessage());
			System.err.println("Action cancel");
		}
		return false;
	}
	
	//effectue l'update d'un computer
	/*
	 * @param oldComputer The old computer
	 * @param name new name
	 * @param dateIntroduced new date (or null)
	 * @param dateDiscontinued new date (or null)
	 * @param company new company (or null)
	 */
	public boolean updateComputer(Computer oldComputer, String name, Date dateIntroduced, Date dateDiscontinued, Company company)
	{
		try {
			return serviceComputer.updateComputer(oldComputer, new Computer(name, dateIntroduced, dateDiscontinued, company ));
				
		} catch (DateDiscontinuedIntroducedException e) {
			System.out.println(e.getMessage());
			System.err.println("Action cancel");
		}
		return false;
	}
	/*
	 * @param id computer id
	 */
	public boolean deleteComputer(long id)
	{
		if(serviceComputer.getComputer(id) != null)
			return serviceComputer.deleteComputer(serviceComputer.getComputer(id));
		else
		{
			System.err.println("Cette ID n'existe pas !");
			return false;
		}
	}
	
	public ServiceComputer getServiceComputer() {
		return serviceComputer;
	}
	//boucle qui vérifie si la date est OK, si KO elle redemande (null fonctionne)
	private Date RequestOkDate()
	{
		Date date = null;
		boolean isValid = false;
		while(!isValid)
		{
			try {
				date = MyUtils.stringToDate(scanner.nextLine().trim());
				isValid = true;
			} catch (ParseException e) {
				System.out.println("Unvalidate format, try again or write null");
				isValid	= false;
			}
		}
		return date;
	}
}
