package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.models.Company;

public class ServiceCompany{
	
	private CompanyDao companyDao;

	public ServiceCompany() {
		companyDao = DaoFactory.getInstance().getCompanyDao();
	}

	public List<Company> getCompanies() {
		return companyDao.getList();
	}
	
	public boolean isCompanyExist(long id)
	{
		return (companyDao.getCompany(id)!= null);
			
	}
	
	public Company getCompany(long id)
	{
		return companyDao.getCompany(id);
			
	}

}
