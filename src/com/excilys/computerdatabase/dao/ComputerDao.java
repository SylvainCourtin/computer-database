package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public interface ComputerDao {

	public boolean add(Computer computer);
	public List<Computer> getList(int limite, int offset);
	public Computer getComputer(long id);
	public boolean delete(Computer computer);
	public boolean update(Computer computer);
	public long getNumberElement();
}
