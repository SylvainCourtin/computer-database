package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.models.Computer;

public interface ComputerDao {

	public long add(Computer computer) throws CompanyDoesNotExistException;
	public List<Computer> getList(int limite, int offset);
	/**
	 * Give element with the computer's name is like sLike
	 * @param limite
	 * @param offset 
	 * @param sLike part of word of computer's name
	 * @return arraylist of computers
	 */
	public List<Computer> getListLike(int limite, int offset, String sLike);
	public Computer getComputer(long id);
	public boolean delete(Computer computer);
	public boolean delete(long id);
	public boolean update(Computer computer);
	public long getNumberElement();
	/**
	 * Give number of element  with the computer's name is like sLike
	 * @param sLike part of word of computer's name
	 * @return number computer found with this %name% 
	 */
	public long getNumberElementLike(String sLike);
}
