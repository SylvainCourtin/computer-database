package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.models.Company;

public class ServiceCompany{
	
	private CompanyDao companyDao;
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
	}
	
	public long getNumberRowComputer()
	{
		return companyDao.getNumberElement();
	}

	/*
	 * @return return the list of all companies in the bdd
	 */
	public List<Company> getCompanies(int limite, int offset) {
		return companyDao.getList(limite,offset);
	}
	
	/*
	 * @param id company id
	 * @return return true if this id company exist in the bdd
	 */
	public boolean isCompanyExist(long id)
	{
		return (companyDao.getCompany(id)!= null);
			
	}
	/*
	 * @param id company id
	 * @return Company finding in the bdd
	 */
	public Company getCompany(long id)
	{
		return companyDao.getCompany(id);
			
	}

}
