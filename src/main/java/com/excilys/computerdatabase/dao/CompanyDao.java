package com.excilys.computerdatabase.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.models.Company;

public interface CompanyDao {
	
	public void add(Company company);
	public List<Company> getList(int limite, int offset);
	public Optional<Company> getCompany(long id);
	public long getNumberElement();

}
