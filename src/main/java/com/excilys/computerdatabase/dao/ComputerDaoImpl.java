package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyConstants;
import com.excilys.computerdatabase.utils.MyUtils;

public class ComputerDaoImpl implements ComputerDao {
	
	private DaoFactory daoFactory;

	public ComputerDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public long add(Computer computer) throws CompanyDoesNotExistException {
		
		long idRes = -1;
		
		if(computer.getName() != null && !computer.getName().equals(""))
		{
			Long companyId = companyExist(computer.getManufacturerCompany());
			
			try(Connection connection = daoFactory.getConnection();
				PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection, MyConstants.SQL_QUERY_COMPUTER_INSERT, true,
					computer.getName(),
					MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()),
					MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()),
					companyId)
				)
			{
				preparedStatement.executeUpdate();
				try(ResultSet resultSet = preparedStatement.getGeneratedKeys();)
				{
					while(resultSet.next())
					{
						idRes = resultSet.getLong(1);
					}
				}

			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		return idRes;
	}
	
	/**
	 * Créer une list de computer grace au résultat  
	 * @param result result of the query
	 * @return list<Computer>
	 * @throws SQLException
	 */
	private List<Computer> getList(ResultSet result) throws SQLException
	{
		List<Computer> computers = new ArrayList<>();
		
		while(result.next())
		{
			Company company = MapperCompany.fromParameters(result.getLong("company.id"), result.getString("company.name"));

			computers.add(MapperComputer.fromParameters(
					result.getLong("computer.id"),
					result.getString("computer.name"),
					result.getDate("computer.introduced"),
					result.getDate("computer.discontinued"),
					company));
		}
		
		return computers;
	}

	@Override
	public List<Computer> getList(int limite, int offset) {
		List<Computer> computers = new ArrayList<>();
		
		try(Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection, MyConstants.SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY_LIMIT,
					false, limite, offset);	
			ResultSet result = preparedStatement.executeQuery();) {

			computers = getList(result);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computers;
	}
	
	@Override
	public List<Computer> getListLike(int limite, int offset, String sLike) {
		List<Computer> computers = new ArrayList<>();
		
		try(Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection, MyConstants.SQL_QUERY_COMPUTER_SELECT_LIKE_LEFT_JOIN_COMPANY_LIMIT,
					false,"%"+sLike+"%", limite, offset);
			ResultSet result = preparedStatement.executeQuery();) 
		{
			computers = getList(result);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computers;
	}

	@Override
	public Optional<Computer> getComputer(long id) {
		Computer computer = null;
		try(Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY,
					false,id);
			ResultSet result = preparedStatement.executeQuery();
			)
		{
			while(result.next())
			{
				Company company = MapperCompany.fromParameters(result.getLong("company.id"), result.getString("company.name"));
	
				computer = MapperComputer.fromParameters(
						result.getLong("computer.id"),
						result.getString("computer.name"),
						result.getDate("computer.introduced"),
						result.getDate("computer.discontinued"),
						company);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(computer);
	}

	@Override
	public boolean delete(Computer computer) {
		return delete(computer.getId());
	}
	
	@Override
	public boolean delete(long id)
	{
		boolean isDelete = false;
		try (Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPUTER_DELETE,false, id);
			)
		{
			if (preparedStatement.executeUpdate() > 0)
					isDelete = true;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isDelete;
	}

	@Override
	public boolean update(Computer computer) throws CompanyDoesNotExistException {
		boolean isUpdate = false;
		if(computer.getName() != null && !computer.getName().equals(""))
		{
			Long companyId = companyExist(computer.getManufacturerCompany());
			
			try(Connection connection = daoFactory.getConnection();
				PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPUTER_UPDATE,false, 
					computer.getName(),
					MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()),
					MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()),
					companyId,
					computer.getId());
				) 
			{				
				
				/*
				preparedStatement.setString(1, computer.getName());
				preparedStatement.setDate(2, MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()));
				preparedStatement.setDate(3, MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()));
				if(computer.getManufacturerCompany() != null)
					preparedStatement.setLong(4, computer.getManufacturerCompany().getId());
				else 
					preparedStatement.setString(4, null);
				//Where id=?
				preparedStatement.setLong(5, computer.getId());
				*/
				if (preparedStatement.executeUpdate() > 0)
					isUpdate = true;	
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return isUpdate;
		
	}

	@Override
	public long getNumberElement() {
		long nbElement = 0;
		try(Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_COUNT);
			) 
		{
			while(result.next())
			{
				nbElement = result.getLong(MyConstants.COUNT);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return nbElement;
	}

	@Override
	public long getNumberElementLike(String sLike) {
		long nbElement = 0;
		try(Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPUTER_COUNT_LIKE,false,"%"+sLike+"%");
			ResultSet result = preparedStatement.executeQuery();
			) 
		{			
			while(result.next())
			{
				nbElement = result.getLong(MyConstants.COUNT);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return nbElement;
	}
	
	@Override
	public long getNumberComputerRelatedToThisCompany(long idCompany) throws CompanyDoesNotExistException {
		long nbElement = 0;
		companyExist(idCompany);
		try(Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPUTER_COUNT_RELATED_COMPANY,false,idCompany);
			ResultSet result = preparedStatement.executeQuery();
			) 
		{			
			while(result.next())
			{
				nbElement = result.getLong(MyConstants.COUNT_COMPUTER);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return nbElement;
	}

	@Override
	public long deleteRelatedToCompany(long idCompany) throws CompanyDoesNotExistException {
		long nbElement = 0;
		companyExist(idCompany);
		try(Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPUTER_DELETE_RELATED_COMPANY,false,idCompany);) 
		{			
			nbElement = preparedStatement.executeUpdate();
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
	/**
	 * Return the id of this company
	 * @param company
	 * @return id of this company or null if doesn't exist
	 * @throws CompanyDoesNotExistException 
	 */
	private Long companyExist(Company company) throws CompanyDoesNotExistException
	{
		if(company != null)
			if(!daoFactory.getCompanyDao().getCompany(company.getId()).isPresent())
				throw new CompanyDoesNotExistException("This computer got an company who doesn't exist in the bdd, please add this company before adding this computer");
			else
				return company.getId();
		else
			return null;
	}
	
	private void companyExist(long idCompany) throws CompanyDoesNotExistException
	{
		if(!daoFactory.getCompanyDao().getCompany(idCompany).isPresent())
			throw new CompanyDoesNotExistException("This computer got an company who doesn't exist in the bdd, please add this company before adding this computer");		
	}
}
