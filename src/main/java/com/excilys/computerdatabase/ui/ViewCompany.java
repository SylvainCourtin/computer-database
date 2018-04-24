package com.excilys.computerdatabase.ui;

import static com.excilys.computerdatabase.utils.MyConstants.NUMBER_LIST_PER_PAGE;

import java.util.Scanner;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.ui.page.PageCompany;

public class ViewCompany {
	
	private ServiceCompany serviceCompany;
	private Scanner scanner = new Scanner(System.in);
	public ViewCompany() {
		serviceCompany = ServiceCompany.getInstance();
	}
	
	/**
	 * Gere l'affichage d'une page company, affiche un certain nombre de company, et demande si l'utilisateur veut changer de page ou revenir au menu
	 */
	public void showList()
	{
		//On récupere tout la 1er page des 10 premiers element, et on récupere le nombre d'élements total dans la bdd
		PageCompany pageCompany = new PageCompany(serviceCompany.getCompanies(NUMBER_LIST_PER_PAGE,0), serviceCompany.getNumberRowComputer());
		pageCompany.getInfoPage();
		int tmpOldPage = 0;//evite de réafficher la meme page si elle a deja été affiché 
		while(!pageCompany.menuPage())
		{
			if(pageCompany.getChoixPages() != tmpOldPage)
			{
				pageCompany.setCompanies(serviceCompany.getCompanies(NUMBER_LIST_PER_PAGE, pageCompany.getChoixPages()*NUMBER_LIST_PER_PAGE));
				pageCompany.getInfoPage();
				tmpOldPage = pageCompany.getChoixPages() ;
			}
			
		}
	}
	
	public ServiceCompany getServiceCompany() {
		return serviceCompany;
	}
	
	/**
	 * demande à l'utilisateur un ID d'une company, s'il ecrit null, on revoit null, sinon on regarde que l'id existe bien
	 * @return retourne une company valide qui existe dans la bdd (ou null)
	 */
	public Company showRequestCompany()
	{
		Company company = null;
		boolean isIDValid = false;
		do {
			String tmp = scanner.nextLine();
			if(!tmp.equals("null"))
			{
				company = serviceCompany.getCompany(Long.parseLong(tmp));
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
		return company;
	}

}
