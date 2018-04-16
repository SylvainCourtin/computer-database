package com.excilys.computerdatabase.models;

import java.util.ArrayList;
import java.util.List;

public class Company {
	
	public String name;
	
	public List<Computer> computersMade;
	
	public Company() {
		computersMade = new ArrayList<>();
	}
	
	public Company(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setComputersMade(List<Computer> computersMade) {
		this.computersMade = computersMade;
	}
	
	public List<Computer> getComputersMade() {
		return computersMade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((computersMade == null) ? 0 : computersMade.hashCode());
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
		Company other = (Company) obj;
		if (computersMade == null) {
			if (other.computersMade != null)
				return false;
		} else if (!computersMade.equals(other.computersMade))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
