package com.excilys.computerdatabase.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public class PageComputer extends Page {
	
	protected List<Computer> computers = new ArrayList<>();
	
	public static PageComputer getInstance(List<Computer> computers)
	{
		return new PageComputer(computers);
	}

	public PageComputer(List<Computer> computers)
	{
		super();
		this.computers = computers;
		
		int res=0; //la dernier page n'est pas entiere
		if(this.computers.size()%NUMBER_LIST_PER_PAGE > 0)
			res=1;
		numberOfPages=this.computers.size()/NUMBER_LIST_PER_PAGE + res;
	}

	@Override
	public void getNumberPage(int page) {
		int tmpPage=page-1; // la page 0 == 1 pour faciliter entre l'affichage a l'utilisateur et la gestion de la liste
		System.out.println("Computer : \n");
		for(int i = tmpPage*NUMBER_LIST_PER_PAGE; i < (tmpPage+1)*NUMBER_LIST_PER_PAGE && i < computers.size(); i++)
		{
			System.out.println(computers.get(i).toString());
		}
		System.out.println("---------------------------");
		System.out.println("\t"+page+"/"+numberOfPages);//page actuelle sur page max
		System.out.println("---------------------------");
	}
	
	
}
