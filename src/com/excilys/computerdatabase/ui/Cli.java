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
	
	
	//affichage du menu principale
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
	//effectue l'affichage et le choix des options
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
			requestDeleteComputer(requestIdComputer());
			break;
		case 7:
			return false;
		}
		return true;
	}
	//demande d'un id de computer à l'utilisateur
	public long requestIdComputer()
	{
		System.out.println("\n\n\n----------------------------");
		System.out.println("Computer id ?");
		System.out.print("= ");
		return scanner.nextLong();
		
	}
	//affiche les infos pour un seul computer
	public void displayOneComputer(long id)
	{
		computer.showOneComputer(id);
		System.out.println("\n\n\n----------------------------");
	}
	//effectue l'effacement d'un computer dans la bdd 
	public void requestDeleteComputer(long id)
	{
		if(computer.deleteComputer(id))
		{
			System.out.println("Success !");
		}
		else
			System.out.println("Echec :(");
		System.out.println("\n\n\n----------------------------");
	}
	
	//effectue une création un nouveau computer
	public void requestNewComputer()
	{
		String name = computer.showRequestName();
		Date dateIntroduced= computer.showRequestDateIntroduced();
		Date dateDiscontinued = computer.showRequestDateDiscontinued();
		Company company = this.company.showRequestCompany();
		
		
		if(computer.requestCreateComputer(name,dateIntroduced,dateDiscontinued,company))
			System.out.println("Successfully added !");
		else
			System.out.println("Echec :(");
	}
	//met à jour un computer deja existant dans la base
	public void requestUpdateComputer()
	{
		long id = requestIdComputer();
	}

	public static void main(String[] args) {
		System.out.println("---------Welcome on computer-database---------");
		do {
			
		}while(Cli.getInstance().doAction());
		System.out.println("---------Disconnected---------");
	}

}
