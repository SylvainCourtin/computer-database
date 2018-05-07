package com.excilys.computerdatabase.dao;

import java.util.List;
import java.util.Optional;

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
	public Optional<Computer> getComputer(long id);
	public boolean delete(Computer computer);
	public boolean delete(long id);
	public boolean update(Computer computer) throws CompanyDoesNotExistException;
	public long getNumberElement();
	/**
	 * Give number of element  with the computer's name is like sLike
	 * @param sLike part of word of computer's name
	 * @return number computer found with this %name% 
	 */
	public long getNumberElementLike(String sLike);
	
	/**
	 * Return the number of row in the bdd linked with this company
	 * @param idCompany the id of the Company 
	 * @return Return the number of row in the bdd linked with this company
	 * @throws CompanyDoesNotExistException 
	 */
	public long getNumberComputerRelatedToThisCompany(long idCompany) throws CompanyDoesNotExistException;
	
	/**
	 * return the number of row was been deleted
	 * @param idCompany
	 * @return return the number of row was been deleted.
	 * @throws CompanyDoesNotExistException 
	 */
	public long deleteRelatedToCompany(long idCompany) throws CompanyDoesNotExistException;
}
