package com.excilys.computerdatabase.ui;

import java.util.Scanner;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;

public class ViewCompany {
	
	private ServiceCompany serviceCompany;
	private Scanner scanner = new Scanner(System.in);
	public ViewCompany() {
		serviceCompany = new ServiceCompany();
	}
	
	public void showList()
	{
		for (Company c : serviceCompany.getCompanies()) {
			System.out.println(c.toString());
		}
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
			System.out.println("----------------------------");
			System.out.println("Company id ?\nCan be null");
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
