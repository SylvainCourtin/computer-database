package com.excilys.computerdatabase.ui;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

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
			requestUpdateComputer();
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
		System.out.println("\n-----------Create Computer-----------------");
		System.out.println("Name ?");
		String name = computer.showRequestName();
		
		System.out.println("----------------------------");
		System.out.println("DateIntroduced ?\nexample : dd-MM-yyyy. You can also write null");
		LocalDate dateIntroduced= computer.showRequestDateIntroduced();
		
		System.out.println("----------------------------");
		System.out.println("DateDiscontinued ? \n/!\\ Must be more greater than DateIntroduced  \nexample : dd-MM-yyyy. You can also write null");
		LocalDate dateDiscontinued = computer.showRequestDateDiscontinued();
		
		System.out.println("----------------------------");
		System.out.println("Company id ?\nCan be null");
		Company company = this.company.showRequestCompany();
		
		long id = computer.createComputer(name,dateIntroduced,dateDiscontinued,company);
		if( id > 0) {
			System.out.println("Successfully added !");
			System.out.println("New ID : "+ id);
		}
		else
			System.out.println("Echec :(");
	}
	//met à jour un computer deja existant dans la base
	public void requestUpdateComputer()
	{
		long id = requestIdComputer();
		Optional<Computer> oldComputer = computer.getServiceComputer().getComputer(id);
		if( oldComputer.isPresent())
		{
			System.out.println("\n-----------Old Computer-----------------");
			//On affiche les anciennes informations
			computer.showOneComputer(id);
			
			System.out.println("New name ?");
			String name = computer.showRequestName();
			
			System.out.println("----------------------------");
			System.out.println("DateIntroduced ?\nexample : dd-MM-yyyy. You can also write null");
			LocalDate dateIntroduced= computer.showRequestDateIntroduced();
			
			System.out.println("----------------------------");
			System.out.println("DateDiscontinued ? \n/!\\ Must be more greater than DateIntroduced  \nexample : dd-MM-yyyy. You can also write null");
			LocalDate dateDiscontinued = computer.showRequestDateDiscontinued();
			
			System.out.println("----------------------------");
			System.out.println("Company id ?\nCan be null");
			Company company = this.company.showRequestCompany();
			
			if(computer.updateComputer(oldComputer.get(),name,dateIntroduced,dateDiscontinued,company))
				System.out.println("Successfully Updated !");
			else
				System.out.println("Echec :(");
		}
		else
			System.err.println("Cette ID n'existe pas !");
	}

	public static void main(String... args) {
		System.out.println("---------Welcome on computer-database---------");
		do {
			
		}while(Cli.getInstance().doAction());
		System.out.println("---------Disconnected---------");
	}
}
