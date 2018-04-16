package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.models.Company;

public class CompanyDaoImpl implements CompanyDao {
	
	private DaoFactory daoFactory;

	public CompanyDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void add(Company company) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Company> getList() {
		// TODO Auto-generated method stub
		return null;
	}

}
