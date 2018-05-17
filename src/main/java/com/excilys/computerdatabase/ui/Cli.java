package com.excilys.computerdatabase.ui;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public final class Cli {
	
	private Scanner scanner = new Scanner(System.in);
	private ViewCompany company;
	private ViewComputer computer;
	
	private Cli(ApplicationContext context) {
		company = new ViewCompany(context);
		computer = new ViewComputer(context);
	}
	
	
	/**
	 * Affiche le menu principal
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws InputMismatchException
	 * @throws NumberFormatException
	 */
	public MenuCli displayAction() throws ArrayIndexOutOfBoundsException, InputMismatchException, NumberFormatException
	{
		
		System.out.println("Selection your option ? ");
		System.out.println("----------------------------");
		System.out.println("-1 Get all computers -");
		System.out.println("-2 Get all companies -");
		System.out.println("-3 Show computer details -");
		System.out.println("-4 Add a computer -");
		System.out.println("-5 Update a computer -");
		System.out.println("-6 Delete a computer -");
		System.out.println("-7 Delete a company -");
		System.out.println("-8 Exit -");
		System.out.println("----------------------------");
		
		String sValues = scanner.nextLine();
		Long values = Long.valueOf(sValues);
		if(values > MenuCli.values().length || values < 0)
			throw new ArrayIndexOutOfBoundsException("No menu corresponding at this number");
		else
			return MenuCli.values()[values.intValue()-1];
	}
	//effectue l'affichage et le choix des options
	public boolean doAction()
	{
		try {
			MenuCli option = displayAction();
			if(option != null)
			{
				switch(option) {
				case GET_ALL_COMPUTERS:
					computer.showList();
					break;
				case GET_ALL_COMPANIES:
					company.showList();
					break;
				case SHOW_ONE_COMPUTER:
					displayOneComputer(requestIdComputer());
					break;
				case ADD_COMPUTER:
					requestNewComputer();
					break;
				case UPDATE_COMPUTER:
					requestUpdateComputer();
					break;
				case DELETE_COMPUTER:
					requestDeleteComputer(requestIdComputer());
					break;
				case DELETE_COMPANY:
					requestDeleteCompany();
					break;
				case QUIT:
					return false;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}catch (InputMismatchException | NumberFormatException e) {
			System.out.println("Oops wrong value try again");
		}
		return true;
	}
	//demande d'un id de computer à l'utilisateur
	public long requestIdComputer()
	{
		System.out.println("\n\n\n----------------------------");
		System.out.println("Computer id ?");
		System.out.print("= ");
		String sValues = scanner.nextLine();
		return Long.valueOf(sValues);
		
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
	
	public void requestDeleteCompany()
	{
		System.out.println("\n--------------Delete Company--------------");
		System.out.println("Company id ?\nor enter quit to cancel this action");
		Company company = this.company.showRequestCompanyNotNull();
		if(company != null)
		{
			try {
				long numberOfComputerLinked = computer.getNumberComputerGotThisCompany(company.getId());
				StringBuilder message = new StringBuilder();
				message.append("The company : ");
				message.append(company.getName());
				message.append(" will be deleted. Moreover, ");
				message.append(numberOfComputerLinked);
				message.append(" computers will be deleted too. Cause they are linked with this company.\nAre you sure too deleted this company ?");
				System.out.println(message.toString());
				if(readConfirmation())
				{
					if(this.company.deleteCompany(company.getId()))
						System.out.println("Success !");
					else
						System.out.println("Echec !");
				}
				else
				{
					System.out.println("Cancel action");
				}
				
			} catch (CompanyDoesNotExistException e) {
				e.printStackTrace();
				System.out.println("fail.");
			}	
		}
		else
			System.out.println("Cancel action");
	}
	
	public boolean readConfirmation()
	{
		String response = scanner.next();
		if(response.contains("yes") || response.equals("y"))
			return true;
		else if(response.contains("no") || response.equals("n"))
			return false;
		else
		{
			System.out.println("Write yes or no please");
			return readConfirmation();
		}
	}

	public static void main(String... args) {
		ApplicationContext context = 
		          new AnnotationConfigApplicationContext(Application.class);
		Cli cli = new Cli(context);
		System.out.println("---------Welcome on computer-database---------");
		do {
			
		}while(cli.doAction());
		System.out.println("---------Disconnected---------");
	}
}
