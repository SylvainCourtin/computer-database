package com.excilys.computerdatabase.utils;

import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public interface ControllerInterface {
	
	public List<Computer> getComputers();
	
	public boolean deleteComputer(Computer computer);
	
	public void addComputer(Computer computer);
	
	public boolean updateComputer(Computer oldComputer, Computer newComputer);

}
