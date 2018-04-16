package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.models.Company;

public interface CompanyDao {
	
	public void add(Company company);
	
	public List<Company> getList();
	
	public Company getCompany(long id);

}
