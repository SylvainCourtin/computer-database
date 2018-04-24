package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.mappers.MapperCompany;
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
			PreparedStatement preparedStatement = connection.prepareStatement(MyConstants.SQL_QUERY_COMPANY_INSERT);
			preparedStatement.setString(1, company.getName());
			preparedStatement.executeUpdate();
			connection.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Company> getList(int limite, int offset) {
		List<Company> companies = new ArrayList<>();
		
		try {
			Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(MyConstants.SQL_QUERY_COMPANY_SELECT_LIMIT);
			preparedStatement.setInt(1, limite);
			preparedStatement.setInt(2, offset);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next())
			{
				
				companies.add(MapperCompany.fromParameters(
						result.getLong("id"),
						result.getString("name")));
			}
			result.close();
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
			
			while(result.next())
			{
				company = MapperCompany.fromParameters(
						id,
						result.getString("name"));
			}
			result.close();
			connection.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return company;
	}

	@Override
	public long getNumberElement() {
		long nbElement = 0;
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPANY_COUNT);
			while(result.next())
			{
				nbElement = result.getInt("COUNT(*)");
			}
			
			result.close();
			connection.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return nbElement;
	}
}