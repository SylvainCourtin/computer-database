package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			try {
				
				//on vérifie d'abord si la company n'existe pas avant de l'insérer dans la table, sinon on crée la company
				if(computer.getManufacturerCompany() != null && daoFactory.getCompanyDao().getCompany(computer.getManufacturerCompany().getId()) == null)
					throw new CompanyDoesNotExistException("This computer got an company who doesn't exist in the bdd, please add this company before adding this computer");
				
				Connection connection = daoFactory.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(MyConstants.SQL_QUERY_COMPUTER_INSERT, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, computer.getName());
				preparedStatement.setDate(2, MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()));
				preparedStatement.setDate(3, MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()));
				if(computer.getManufacturerCompany() != null)
					preparedStatement.setLong(4, computer.getManufacturerCompany().getId());
				else 
					preparedStatement.setString(4, null);

				preparedStatement.executeUpdate();
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				while(resultSet.next())
				{
					idRes = resultSet.getLong(1);
				}
				connection.close();
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		return idRes;
	}

	@Override
	public List<Computer> getList(int limite, int offset) {
		List<Computer> computers = new ArrayList<>();
		
		try {
			Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(MyConstants.SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY_LIMIT);
			preparedStatement.setInt(1, limite);
			preparedStatement.setInt(2, offset);
			ResultSet result = preparedStatement.executeQuery();
			
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
			result.close();
			connection.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computers;
	}

	@Override
	public Computer getComputer(long id) {
		Computer computer = null;
		try {
			Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(MyConstants.SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY);
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();

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
			
			result.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computer;
	}

	@Override
	public boolean delete(Computer computer) {
		boolean isDelete = false;
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			if(statement.executeUpdate(MyConstants.SQL_QUERY_COMPUTER_DELETE+computer.getId() +";") > 0)
					isDelete = true;

			connection.close();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isDelete;
	}

	@Override
	public boolean update(Computer computer) {
		boolean isUpdate = false;
		try {
			
			//on vérifie d'abord si la company n'existe pas avant de l'insérer dans la table, sinon on crée la company
			if(computer.getManufacturerCompany() != null && daoFactory.getCompanyDao().getCompany(computer.getManufacturerCompany().getId()) == null)
				throw new CompanyDoesNotExistException("This computer got an company who doesn't exist in the bdd, please add this company before adding this computer");

			
			Connection connection = daoFactory.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(MyConstants.SQL_QUERY_COMPUTER_UPDATE);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()));
			preparedStatement.setDate(3, MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()));
			if(computer.getManufacturerCompany() != null)
				preparedStatement.setLong(4, computer.getManufacturerCompany().getId());
			else 
				preparedStatement.setString(4, null);
			//Where id=?
			preparedStatement.setLong(5, computer.getId());
			if(computer.getManufacturerCompany() != null)
				preparedStatement.setLong(4, computer.getManufacturerCompany().getId());
			else 
				preparedStatement.setString(4, null);
			if (preparedStatement.executeUpdate() > 0)
				isUpdate = true;

			connection.close();
			
			
			
		}
		catch (SQLException | CompanyDoesNotExistException e) {
			e.printStackTrace();
		}
		return isUpdate;
		
	}

	@Override
	public long getNumberElement() {
		long nbElement = 0;
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_COUNT);
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
