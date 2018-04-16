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

}
