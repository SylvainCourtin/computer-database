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
		String name = computer.showRequestName();
		Date dateIntroduced= computer.showRequestDateIntroduced();
		Date dateDiscontinued = computer.showRequestDateDiscontinued();
		Company company = this.company.showRequestCompany();
		
		
		computer.requestCreateComputer(name,dateIntroduced,dateDiscontinued,company);
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
