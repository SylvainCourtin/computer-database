package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.models.Computer;

public interface ComputerDao {

	public long add(Computer computer) throws CompanyDoesNotExistException;
	public List<Computer> getList(int limite, int offset);
	public List<Computer> getList(int limite, int offset, String sLike);
	public Computer getComputer(long id);
	public boolean delete(Computer computer);
	public boolean update(Computer computer);
	public long getNumberElement();
}
