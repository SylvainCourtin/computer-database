package com.excilys.computerdatabase.service;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.models.Company;

public class ServiceCompany{
	
	private CompanyDao companyDao;
	private ComputerDao computerDao;
	private static ServiceCompany serviceCompany;
	
	public static ServiceCompany getInstance()
	{
		if(serviceCompany == null)
		{
			return new ServiceCompany();
		}
		return serviceCompany;
	}

	public ServiceCompany() {
		companyDao = DaoFactory.getInstance().getCompanyDao();
		computerDao = DaoFactory.getInstance().getComputerDao();
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
	 * @param idCompany
	 * @return the number of computer who got the same company given in arg
	 * @throws CompanyDoesNotExistException 
	 */
	public long getNumberOfComputerWithCompany(long idCompany) throws CompanyDoesNotExistException
	{
		return computerDao.getNumberComputerRelatedToThisCompany(idCompany);
	}
	
	/**
	 * delete all computer linked with this company before to delete the company
	 * @param id
	 * @return return false if didn't work
	 * @throws CompanyDoesNotExistException 
	 */
	public boolean deleteCompanyAndAllComputerRelatedToThisCompany(long id) throws CompanyDoesNotExistException
	{
		computerDao.deleteRelatedToCompany(id);
		return companyDao.deleteCompany(id);
	}

}
