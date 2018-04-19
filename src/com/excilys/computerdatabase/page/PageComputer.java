package com.excilys.computerdatabase.page;

import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public class PageComputer extends Page {

	public PageComputer(List<Computer> computers)
	{
		super();
		this.computers = computers;
		
		int res=0; //la dernier page n'est pas entiere
		if(this.companies.size()%NUMBER_LIST_PER_PAGE > 0)
			res=1;
		numberOfPages=this.companies.size()/NUMBER_LIST_PER_PAGE + res;
	}

	@Override
	public void getNumberPage(int page) {
	}
	
	
}
