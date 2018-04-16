package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class Controller implements ControllerInterface {
	
	private List<Computer> computers;
	private List<Company> companies;
	
	

	public Controller() {
		computers = new ArrayList<>();
	}

	@Override
	public List<Computer> getComputers() {
		return computers;
	}

	@Override
	public boolean deleteComputer(Computer computer) {
		
		return computers.remove(computer);
	}

	@Override
	public void addComputer(Computer computer) {
		computers.add(computer);
	}

	@Override
	public boolean updateComputer(Computer oldComputer, Computer newComputer) {
		for (Computer computer : computers) 
		{
			if(computer.equals(oldComputer))
			{
				computer = newComputer;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Computer getComputer(String name)
	{
		for (Computer computer : computers) 
		{
			if(computer.getName().equals((name)))
			{
				return computer;
			}
		}
		return null;
	}
	
	@Override
	public List<Company> getCompanies() {
	
		return companies;
	}
	
	@Override
	public void createComputer(String name, Date dateIntroduced, Date dateDiscontinued, Company manufacturerCompany) throws DateDiscontinuedIntroducedException {
		computers.add(new Computer(name, dateIntroduced, dateDiscontinued, manufacturerCompany));
		
	}
	
	@Override
	public String getDetailsComputer(Computer computer) {
		return computer.toString();
	}

}
