package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyConstants;

public class ComputerDaoImpl implements ComputerDao {
	
	private DaoFactory daoFactory;

	public ComputerDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public boolean add(Computer computer) {
		
		boolean isAdd = false;
		
		try {
			
			//on vérifie d'abord si la company n'existe pas avant de l'insérer dans la table, sinon on crée la company
			if(daoFactory.getCompanyDao().getCompany(computer.getManufacturerCompany().getId()) == null)
				daoFactory.getCompanyDao().add(computer.getManufacturerCompany());
			
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_INSERT+'('+
					computer.getId()+','+
					computer.getName()+','+
					computer.getDateIntroduced()+','+
					computer.getDateDiscontinued()+','+
					computer.getManufacturerCompany().getId()+','+
					");");
			
			isAdd = result.rowInserted();
			
			connection.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdd;
	}

	@Override
	public List<Computer> getList() {
		List<Computer> computers = new ArrayList<>();
		
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_SELECT+";");
			
			while(result.next())
			{
				
				Company company = null;
				//La bdd peut nous retourner un long primitif null pour l'id de la company. On effectue un try catch
				try {
					company = daoFactory.getCompanyDao().getCompany(result.getLong("company_id"));
				}
				catch(Exception e)
				{
					
				}
				
				computers.add(new Computer(
						result.getLong("id"),
						result.getString("name"),
						result.getDate("introduced"),
						result.getDate("discontinued"),
						company));
			}
			
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
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_SELECT+"WHERE id="+ id +";");
			
			Company company = null;
			//La bdd peut nous retourner un long primitif null pour l'id de la company. On effectue un try catch
			try {
				company = daoFactory.getCompanyDao().getCompany(result.getLong("company_id"));
			}
			catch(Exception e)
			{
				
			}
			
			computer = new Computer(
					result.getLong("id"),
					result.getString("name"),
					result.getDate("introduced"),
					result.getDate("discontinued"),
					company);
			
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
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_DELETE+"id="+ computer.getId() +";");
			
			isDelete = result.rowDeleted();
			
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
			
			String companyId = "  company_id =";
			if(computer.getManufacturerCompany() != null )
				companyId.concat(" "+computer.getManufacturerCompany().getId());
			else
				companyId.concat(" null");
			
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_DELETE+
					" name = "+ computer.getName() +
					" introduced = "+ computer.getDateIntroduced() +
					" discontinued = "+ computer.getDateDiscontinued() +
					" company_id = "+ companyId +
					" WHERE id= "+ computer.getId() +";");
			
			
			isUpdate=result.rowUpdated();
			connection.close();
			
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdate;
		
	}
}
