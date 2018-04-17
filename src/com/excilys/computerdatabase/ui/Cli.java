package com.excilys.computerdatabase.ui;

import java.text.ParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.utils.MyUtils;

public final class Cli {
	
	private Scanner scanner = new Scanner(System.in);
	private ViewCompany company = new ViewCompany();
	private ViewComputer computer = new ViewComputer();
	
	public static Cli getInstance()
	{
		return new Cli();
	}
	
	public int displayAction()
	{
		
		System.out.println("Selection your option ? ");
		System.out.println("----------------------------");
		System.out.println("-1 Get all computers -");
		System.out.println("-2 Get all companies -");
		System.out.println("-3 Show computer details -");
		System.out.println("-4 Add a computer -");
		System.out.println("-5 Update a computer -");
		System.out.println("-6 Delete a computer -");
		System.out.println("-7 Exit -");
		System.out.println("----------------------------");
		int i = 0;
		try {
			 i = scanner.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Oops wrong value try again");
		}
		
		return i;
	}
	
	public boolean doAction()
	{
		int option = displayAction();
		
		switch(option) {
		case 1:
			computer.showList();
			break;
		case 2:
			company.showList();
			break;
		case 3:
			displayOneComputer(requestIdComputer());
			break;
		case 4:
			requestNewComputer();
			break;
		case 5:
			
			break;
		case 6:
			deleteComputer(requestIdComputer());
			break;
		case 7:
			return false;
		}
		return true;
	}
	
	public long requestIdComputer()
	{
		System.out.println("\n\n\n----------------------------");
		System.out.println("Computer id ?");
		System.out.print("= ");
		return scanner.nextLong();
		
	}
	
	public void displayOneComputer(long id)
	{
		computer.showOneComputer(id);
		System.out.println("\n\n\n----------------------------");
	}
	
	public void deleteComputer(long id)
	{
		if(computer.getServiceComputer().deleteComputer(computer.getServiceComputer().getDetailsComputer(id)))
		{
			System.out.println("Success !");
		}
		else
			System.out.println("Echec :(");
		System.out.println("\n\n\n----------------------------");
	}
	
	public void requestNewComputer()
	{
		String name = "";
		Date dateIntroduced= null;
		Date dateDiscontinued = null;
		Company company = null;
		
		System.out.println("\n-----------Create Computer-----------------");
		System.out.println("Name ?");
		
		name = scanner.nextLine();
		
		System.out.println("----------------------------");
		System.out.println("DateIntroduced ?\nexample : 1975-01-01. You can also write null");
		dateIntroduced= RequestOkDate();
		
		System.out.println("----------------------------");
		System.out.println("DateDiscontinued ? \n/!\\ Must be more greater than DateIntroduced  \nexample : 1975-01-01. You can also write null");
		dateDiscontinued= RequestOkDate();
		boolean isIDValid = false;
		do {
			System.out.println("----------------------------");
			System.out.println("Company id ?\nCan be null");
			String tmp = scanner.nextLine();
			if(!tmp.equals("null"))
			{
				company = this.company.getServiceCompany().getCompany(Long.parseLong(tmp));
				if( company == null)
				{
					isIDValid = false;
					System.out.println("\n\t This company doesn't exist try again");
				}
				else
					isIDValid = true;
			}
			else
			{
				isIDValid = true;
			}
		}while(!isIDValid);
		
		try {
			computer.getServiceComputer().addComputer(name, dateIntroduced, dateDiscontinued, company );
		} catch (DateDiscontinuedIntroducedException e) {
			System.out.println(e.getMessage());
			System.out.println("Action cancel");
		}
	}
	
	public Date RequestOkDate()
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

	public static void main(String[] args) {
		System.out.println("---------Welcome on computer-database---------");
		do {
			
		}while(Cli.getInstance().doAction());
		System.out.println("---------Disconnected---------");
	}

}
