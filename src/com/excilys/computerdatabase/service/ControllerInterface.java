package com.excilys.computerdatabase.service;

import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.DateDiscontinuedIntroducedException;

public interface ControllerInterface {
	
	public List<Computer> getComputers();
	
	public Computer getComputer(String name);
	
	public boolean deleteComputer(Computer computer);
	
	public void addComputer(Computer computer);
	
	public boolean updateComputer(Computer oldComputer, Computer newComputer);
	
	public List<Company> getCompanies();
	
	public String getDetailsComputer(Computer computer);
	
	public void createComputer(String name, Date dateIntroduced, Date dateDiscontinued, Company manufacturerCompany) throws DateDiscontinuedIntroducedException;

	
}
