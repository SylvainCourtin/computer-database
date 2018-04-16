package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.utils.MyConstants;

public class CompanyDaoImpl implements CompanyDao {
	
	private DaoFactory daoFactory;

	public CompanyDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void add(Company company) {
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_INSERT+'('+
					company.getId()+','+
					company.getName()+','+
					");");
			
			connection.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Company> getList() {
		List<Company> companies = new ArrayList<>();
		
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPANY_SELECT+";");
			
			while(result.next())
			{
				
				companies.add(new Company(
						result.getLong("id"),
						result.getString("name")));
			}
			
			connection.close();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return companies;
	}
	
	@Override
	public Company getCompany(long id)
	{
		Company company = null;
		
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPANY_SELECT+"WHERE id="+id+";");
			company = new Company(
					result.getLong("id"),
					result.getString("name"));
			
			connection.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return company;
	}
}
