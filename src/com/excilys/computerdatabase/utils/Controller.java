package com.excilys.computerdatabase.utils;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public class Controller implements ControllerInterface {
	
	private List<Computer> computers;
	
	

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

}
