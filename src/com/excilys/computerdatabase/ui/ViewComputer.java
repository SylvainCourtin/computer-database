package com.excilys.computerdatabase.ui;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
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
		for (Computer c : serviceComputer.getComputers()) {
			System.out.println(c.toString());
		}
	}
	//affichage d'un pc
	public void showOneComputer(long id)
	{
		System.out.println(serviceComputer.getDetailsComputer(id).toString());
	}
	
	//demande un nom (pour la création d'un computer ou update)
	public String showRequestName()
	{
		System.out.println("\n-----------Create Computer-----------------");
		System.out.println("Name ?");
		
		return scanner.nextLine();
	}
	
	//demande d'une date ou null (format exigeant !)
	public Date showRequestDateIntroduced()
	{
		System.out.println("----------------------------");
		System.out.println("DateIntroduced ?\nexample : 1975-01-01. You can also write null");
		return RequestOkDate();
	}
	//demande d'une date ou null (format exigeant !)
	public Date showRequestDateDiscontinued()
	{
		System.out.println("----------------------------");
		System.out.println("DateDiscontinued ? \n/!\\ Must be more greater than DateIntroduced  \nexample : 1975-01-01. You can also write null");
		return RequestOkDate();
		
	}
	//effectue la création d'un computer
	public boolean requestCreateComputer(String name, Date dateIntroduced, Date dateDiscontinued, Company company)
	{
		try {
			return serviceComputer.addComputer(name, dateIntroduced, dateDiscontinued, company );
				
		} catch (DateDiscontinuedIntroducedException e) {
			System.out.println(e.getMessage());
			System.out.println("Action cancel");
		}
		return false;
	}
	
	public boolean deleteComputer(long id)
	{
		return serviceComputer.deleteComputer(serviceComputer.getDetailsComputer(id));
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
				date = MyUtils.stringToDate(scanner.nextLine());
				isValid = true;
			} catch (ParseException e) {
				System.out.println("Unvalidate format, try again or write null");
				isValid	= false;
			}
		}
		return date;
	}
}
