package com.excilys.computerdatabase.dtos;

import com.excilys.computerdatabase.models.Company;

public class CompanyBasicView {
	
	private long id;
	private String name;
	
	public CompanyBasicView()
	{
	}

	public CompanyBasicView(Company company) {
		id = company.getId();
		name = company.getName();
	}

	public CompanyBasicView(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
