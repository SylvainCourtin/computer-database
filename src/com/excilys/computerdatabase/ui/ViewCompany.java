package com.excilys.computerdatabase.ui;

import java.util.Scanner;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.page.PageCompany;
import com.excilys.computerdatabase.service.ServiceCompany;

public class ViewCompany {
	
	private ServiceCompany serviceCompany;
	private Scanner scanner = new Scanner(System.in);
	public ViewCompany() {
		serviceCompany = new ServiceCompany();
	}
	
	public void showList()
	{
		PageCompany.getInstance(serviceCompany.getCompanies()).menuPageCompany();
	}
	
	public ServiceCompany getServiceCompany() {
		return serviceCompany;
	}
	//demande à l'utilisateur un ID d'une company, s'il ecrit null, on revoit null, sinon on regarde que l'id existe bien
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
