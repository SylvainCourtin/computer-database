package com.excilys.computerdatabase.ui.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public class PageComputer extends Page {
	
	protected List<Computer> computers =  new ArrayList<>();

	public PageComputer(List<Computer> computers, long numberOfRow)
	{
		super();
		this.computers = computers;
		
		int res=0; //la dernier page n'est pas entiere
		if(numberOfRow%NUMBER_LIST_PER_PAGE > 0)
			res=1;
		numberOfPages=numberOfRow/NUMBER_LIST_PER_PAGE + res;
	}

	@Override
	public void getInfoPage() {
		System.out.println("Computer : \n");
		for(Computer computer : computers)
		{
			System.out.println(computer.toString());
		}
		System.out.println("---------------------------");
		System.out.println("\t"+(this.choixPages+1)+"/"+numberOfPages);//page actuelle sur page max
		System.out.println("---------------------------");
	}
	
	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}
}
