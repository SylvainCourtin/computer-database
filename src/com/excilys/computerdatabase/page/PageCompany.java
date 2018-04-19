package com.excilys.computerdatabase.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Company;

public class PageCompany extends Page {
	
	protected List<Company> companies =  new ArrayList<>();
	
	public static PageCompany getInstance(List<Company> companies)
	{
		return new PageCompany(companies);
	}
	
	public PageCompany(List<Company> companies)
	{
		super();
		this.companies = companies;
		int res=0; //la dernier page n'est pas entiere
		if(this.companies.size()%NUMBER_LIST_PER_PAGE > 0)
			res=1;
		numberOfPages=this.companies.size()/NUMBER_LIST_PER_PAGE + res;
	}

	@Override
	public void getNumberPage(int page)
	{
		int tmpPage=page-1; // la page 0 == 1 pour faciliter entre l'affichage a l'utilisateur et la gestion de la liste
		System.out.println("Company : \n");
		for(int i = tmpPage*NUMBER_LIST_PER_PAGE; i < (tmpPage+1)*NUMBER_LIST_PER_PAGE && i < companies.size(); i++)
		{
			System.out.println(companies.get(i).toString());
		}
		System.out.println("---------------------------");
		System.out.println("\t"+page+"/"+numberOfPages);//page actuelle sur page max
		System.out.println("---------------------------");
	}

}
