package com.excilys.computerdatabase.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.models.Company;

public interface CompanyDao {

	public List<Company> getList(int limite, int offset);
	public Optional<Company> getCompany(long id);
	public long getNumberElement();
	/**
	 * Delete a company and all computer linked with him
	 * @param id id company
	 * @return return false is failed
	 * @throws CompanyDoesNotExistException 
	 */
	public boolean deleteCompany(long id) throws CompanyDoesNotExistException;

}
