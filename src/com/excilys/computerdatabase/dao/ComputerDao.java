package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public interface ComputerDao {

	public void add(Computer computer);
	public List<Computer> getList();
	public Computer getComputer(long id);
	public void delete(Computer computer);
	public void update(Computer computer);
}
