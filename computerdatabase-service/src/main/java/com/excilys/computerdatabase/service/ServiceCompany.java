package com.excilys.computerdatabase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.models.Company;

@Service
public class ServiceCompany{
	
	@Autowired
	private CompanyDao companyDao;
	
	public ServiceCompany(CompanyDao companyDao)
	{
		this.companyDao = companyDao;
	}
	
	public long getNumberRowComputer()
	{
		return companyDao.getNumberElement();
	}

	/**
	 * @return return the list of all companies in the bdd
	 */
	public List<Company> getCompanies(int limite, int offset) {
		return companyDao.getList(limite,offset);
	}
	
	/**
	 * @param id company id
	 * @return return true if this id company exist in the bdd
	 */
	public boolean isCompanyExist(long id)
	{
		return (companyDao.getCompany(id)!= null);
			
	}
	/**
	 * @param id company id
	 * @return Company finding in the bdd
	 */
	public Optional<Company> getCompany(long id)
	{
		return companyDao.getCompany(id);
			
	}
	
	/**
	 * delete all computer linked with this company before to delete the company
	 * @param id
	 * @return return false if didn't work
	 * @throws CompanyDoesNotExistException 
	 */
	public boolean deleteCompanyAndAllComputerRelatedToThisCompany(long id) throws CompanyDoesNotExistException
	{
		return companyDao.deleteCompany(id);
	}

}
