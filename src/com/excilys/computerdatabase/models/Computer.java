package com.excilys.computerdatabase.models;

import java.util.Date;

import com.excilys.computerdatabase.utils.DateDiscontinuedIntroducedException;

public class Computer {

	private long id;
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
		if(dateIntroduced != null && dateDiscontinued != null)
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
	
	public void setDateDiscontinued(Date dateDiscontinued) throws DateDiscontinuedIntroducedException {
		dateDiscontinuedGreaterThanIntroduced(dateIntroduced, dateDiscontinued);
		this.dateDiscontinued = dateDiscontinued;
	}
	public void setDateIntroduced(Date dateIntroduced) throws DateDiscontinuedIntroducedException {
		dateDiscontinuedGreaterThanIntroduced(dateIntroduced, dateDiscontinued);
		this.dateIntroduced = dateIntroduced;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Company getManufacturer() {
		return manufacturerCompany;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Company getManufacturerCompany() {
		return manufacturerCompany;
	}

	public void setManufacturerCompany(Company manufacturerCompany) {
		this.manufacturerCompany = manufacturerCompany;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateDiscontinued == null) ? 0 : dateDiscontinued.hashCode());
		result = prime * result + ((dateIntroduced == null) ? 0 : dateIntroduced.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((manufacturerCompany == null) ? 0 : manufacturerCompany.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (dateDiscontinued == null) {
			if (other.dateDiscontinued != null)
				return false;
		} else if (!dateDiscontinued.equals(other.dateDiscontinued))
			return false;
		if (dateIntroduced == null) {
			if (other.dateIntroduced != null)
				return false;
		} else if (!dateIntroduced.equals(other.dateIntroduced))
			return false;
		if (id != other.id)
			return false;
		if (manufacturerCompany == null) {
			if (other.manufacturerCompany != null)
				return false;
		} else if (!manufacturerCompany.equals(other.manufacturerCompany))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		String dateIntro = "empty";
		String dateDisc = "empty";
		String company = "empty";
		
		if(dateDiscontinued != null)
			dateDisc = dateDiscontinued.toString();
		
		if(dateIntroduced != null)
			dateIntro = dateIntroduced.toString();
		
		if(manufacturerCompany != null)
			company =  manufacturerCompany.getName();
		
		return "Computer [name=" + name + ", dateIntroduced=" + dateIntro + ", dateDiscontinued="
				+ dateDisc + ", manufacturerCompany=" + company + "]";
	}
	
	
	public static void dateDiscontinuedGreaterThanIntroduced(Date introduced, Date discontinued) throws DateDiscontinuedIntroducedException
	{
		if(introduced.after(discontinued))
			throw new DateDiscontinuedIntroducedException(introduced.toString() + " must be before than " + discontinued.toString());
	}

	
}
