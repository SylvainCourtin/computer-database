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
	
	public void showOneComputer(long id)
	{
		System.out.println(serviceComputer.getDetailsComputer(id).toString());
	}
	
	public String showRequestName()
	{
		System.out.println("\n-----------Create Computer-----------------");
		System.out.println("Name ?");
		
		return scanner.nextLine();
	}
	
	public Date showRequestDateIntroduced()
	{
		System.out.println("----------------------------");
		System.out.println("DateIntroduced ?\nexample : 1975-01-01. You can also write null");
		return RequestOkDate();
	}
	
	public Date showRequestDateDiscontinued()
	{
		System.out.println("----------------------------");
		System.out.println("DateDiscontinued ? \n/!\\ Must be more greater than DateIntroduced  \nexample : 1975-01-01. You can also write null");
		return RequestOkDate();
		
	}
	
	public void requestCreateComputer(String name, Date dateIntroduced, Date dateDiscontinued, Company company)
	{
		try {
			if(serviceComputer.addComputer(name, dateIntroduced, dateDiscontinued, company ))
				System.out.println("Successfully added !");
		} catch (DateDiscontinuedIntroducedException e) {
			System.out.println(e.getMessage());
			System.out.println("Action cancel");
		}
	}
	
	public ServiceComputer getServiceComputer() {
		return serviceComputer;
	}
	
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
