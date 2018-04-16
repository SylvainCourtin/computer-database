package com.excilys.computerdatabase.models;

import java.util.Date;

import com.excilys.computerdatabase.utils.DateDiscontinuedIntroducedException;

public class Computer {

	private String name;
	private Date dateIntroduced;
	private Date dateDiscontinued;
	
	private Company manufacturerCompany;
	
	
	public Computer() {
		// TODO Auto-generated constructor stub
	}	
	
	public Computer(String name, Date dateIntroduced, Date dateDiscontinued, Company manufacturerCompany) throws DateDiscontinuedIntroducedException {
		super();
		this.name = name;
		dateDiscontinuedGreaterThanIntroduced(dateIntroduced, dateDiscontinued);
		this.dateIntroduced = dateIntroduced;
		this.dateDiscontinued = dateDiscontinued;
		this.manufacturerCompany = manufacturerCompany;
	}

	public Date getDateDiscontinued() {
		return dateDiscontinued;
	}
	public String getName() {
		return name;
	}
	public Date getDateIntroduced() {
		return dateIntroduced;
	}
	
	public void setDateDiscontinued(Date dateDiscontinued) {
		this.dateDiscontinued = dateDiscontinued;
	}
	public void setDateIntroduced(Date dateIntroduced) {
		this.dateIntroduced = dateIntroduced;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Company getManufacturer() {
		return manufacturerCompany;
	}
	
	public static void dateDiscontinuedGreaterThanIntroduced(Date introduced, Date discontinued) throws DateDiscontinuedIntroducedException
	{
		if(! introduced.before(discontinued));
			throw new DateDiscontinuedIntroducedException();
	}
	
}
