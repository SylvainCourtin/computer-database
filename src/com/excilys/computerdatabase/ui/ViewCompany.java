package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;

public class ViewCompany {
	
	private ServiceCompany serviceCompany;
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

}
