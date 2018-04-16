package com.excilys.computerdatabase.dao;

import java.sql.Connection;

import com.excilys.computerdatabase.models.Company;

public class CompanyDao extends Dao<Company> {

	public CompanyDao(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Company find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
