package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		try(Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPANY_INSERT,false,company.getName());
			)
		{
			preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Company> getList(int limite, int offset) {
		List<Company> companies = new ArrayList<>();
		try(
			Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPANY_SELECT_LIMIT,
					false,limite,offset);
			ResultSet result = preparedStatement.executeQuery();)
		{

			while(result.next())
			{
				companies.add(MapperCompany.fromParameters(
						result.getLong("id"),
						result.getString("name")));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companies;
	}
	
	@Override
	public Optional<Company> getCompany(long id)
	{
		Company company = null;
		
		try(Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPANY_SELECT+"WHERE id="+id+" ORDER BY name DESC ;");
			)
		{

			while(result.next())
			{
				company = MapperCompany.fromParameters(
						id,
						result.getString("name"));
			}

		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(company);
	}

	@Override
	public long getNumberElement() {
		long nbElement = 0;
		try(Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPANY_COUNT);) {
			
			while(result.next())
			{
				nbElement = result.getInt("COUNT(*)");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return nbElement;
	}
	
	/**
	 * Prepare et initialise la requete
	 * @param connection
	 * @param sql
	 * @param isReturnKey
	 * @param objects List of args of this request. Should be in the good order
	 * @return
	 * @throws SQLException
	 */
	private static PreparedStatement initPreparedStatementWithParameters(Connection connection, String sql, boolean isReturnKey, Object...objects) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement(sql, isReturnKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		int params = 1;
		for (Object object : objects) {
			preparedStatement.setObject(params, object);
			params++;
		}
		return preparedStatement;
	}
}
