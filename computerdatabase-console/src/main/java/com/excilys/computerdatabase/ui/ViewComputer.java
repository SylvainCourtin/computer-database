package com.excilys.computerdatabase.ui;

import static com.excilys.computerdatabase.utils.MyConstants.NUMBER_LIST_PER_PAGE;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.ui.page.PageComputer;
import com.excilys.computerdatabase.utils.DateUtil;

public class ViewComputer {
	
	private ServiceComputer serviceComputer;
	private Scanner scanner = new Scanner(System.in);
	
	public ViewComputer(ApplicationContext context) {
		serviceComputer = (ServiceComputer) context.getBean("serviceComputer");
	}
	
	/**
	 * Gere l'affichage d'une page computer, affiche un certain nombre de pc, et demande si l'utilisateur veut changer de page ou revenir au menu
	 */
	public void showList()
	{
		PageComputer pageComputer = new PageComputer(serviceComputer.getComputers(NUMBER_LIST_PER_PAGE,0), serviceComputer.getNumberRowComputer());
		//On récupere tout la 1er page des 10 premiers element, et on récupere le nombre d'élements total dans la bdd
		pageComputer.getInfoPage();
		int tmpOldPage = 0;//evite de réafficher la meme page si elle a deja été affiché 
		while(!pageComputer.menuPage())
		{
			if(pageComputer.getChoixPages() != tmpOldPage)
			{
				pageComputer.setComputers(serviceComputer.getComputers(NUMBER_LIST_PER_PAGE, pageComputer.getChoixPages()*NUMBER_LIST_PER_PAGE));
				pageComputer.getInfoPage();
				tmpOldPage = pageComputer.getChoixPages() ;
			}
		}
	}
	//
	/**
	 * affichage d'un pc
	 * @param id computer id
	 */
	public void showOneComputer(long id)
	{
		Optional<Computer> computer = serviceComputer.getComputer(id);
		if( computer.isPresent())
			System.out.println(computer.get().toStringDetails());
		else
			System.err.println("Cette ID n'existe pas !");
	}
	
	/**
	 * demande un nom (pour la création d'un computer ou update)
	 * @return ce qu'a écrit l'utilisateur
	 */
	public String showRequestName()
	{
		return scanner.nextLine().trim();
	}
	
	/**
	 * demande d'une date ou null (format exigeant !)
	 */
	public LocalDate showRequestDateIntroduced()
	{
		return DateUtil.requestOkDate();
	}
	/**
	 * demande d'une date ou null (format exigeant !)
	 */
	public LocalDate showRequestDateDiscontinued()
	{
		return  DateUtil.requestOkDate();
		
	}
	/**
	 * effectue la création d'un computer
	*/
	public long createComputer(String name, LocalDate dateIntroduced, LocalDate dateDiscontinued, Company company)
	{
		try {
			return serviceComputer.addComputer(name, dateIntroduced, dateDiscontinued, company );
				
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException | NoNameComputerException e) {
			System.out.println(e.getMessage());
			System.err.println("Action cancel");
		}
		return -1;
	}
	
	//effectue l'update d'un computer
	/**
	 * @param oldComputer The old computer
	 * @param name new name
	 * @param dateIntroduced new date (or null)
	 * @param dateDiscontinued new date (or null)
	 * @param company new company (or null)
	 */
	public boolean updateComputer(Computer oldComputer, String name, LocalDate dateIntroduced, LocalDate dateDiscontinued, Company company)
	{
		try {
			return serviceComputer.updateComputer(oldComputer, new Computer(name, dateIntroduced, dateDiscontinued, company ));
				
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException | NoNameComputerException e) {
			System.out.println(e.getMessage());
			System.err.println("Action cancel");
		}
		return false;
	}
	/**
	 * @param id computer id
	 */
	public boolean deleteComputer(long id)
	{
		Optional<Computer> computer = serviceComputer.getComputer(id);
		if( computer.isPresent())
			return serviceComputer.deleteComputer(computer.get());
		else
		{
			System.err.println("Cette ID n'existe pas !");
			return false;
		}
	}
	
	public ServiceComputer getServiceComputer() {
		return serviceComputer;
	}
	/**
	 * 
	 * @param idCompany
	 * @return
	 * @throws CompanyDoesNotExistException 
	 */
	public long getNumberComputerGotThisCompany(long idCompany) throws CompanyDoesNotExistException
	{
		long res = 0;
		res = serviceComputer.getNumberOfComputerWithCompany(idCompany);
		return res;
	}
}
