package com.excilys.computerdatabase.ui.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Company;

public class PageCompany extends Page {
	
	protected List<Company> companies =  new ArrayList<>();
	
	public PageCompany(List<Company> companies, long numberOfRow)
	{
		super();
		this.companies = companies;
		int res=0; //la dernier page n'est pas entiere
		if(numberOfRow%NUMBER_LIST_PER_PAGE > 0)
			res=1;
		numberOfPages=numberOfRow/NUMBER_LIST_PER_PAGE + res;
	}

	@Override
	public void getInfoPage()
	{
		System.out.println("Company : \n");
		for(Company company : companies)
		{
			System.out.println(company.toString());
		}
		System.out.println("---------------------------");
		System.out.println("\t"+(this.choixPages+1)+"/"+numberOfPages);//page actuelle sur page max
		System.out.println("---------------------------");
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
	

}
