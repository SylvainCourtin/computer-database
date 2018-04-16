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
	public void add(Computer computer) {
		
		//on vérifie d'abord si la company n'existe pas avant de l'insérer dans la table, sinon on crée la company
		if(daoFactory.getCompanyDao().getCompany(computer.getManufacturerCompany().getId()) == null)
			daoFactory.getCompanyDao().add(computer.getManufacturerCompany());
		
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			
			statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_INSERT+'('+
					computer.getId()+','+
					computer.getName()+','+
					computer.getDateIntroduced()+','+
					computer.getDateDiscontinued()+','+
					computer.getManufacturerCompany().getId()+','+
					");");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
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
				if(result.getString("company_id") != null)
					company = daoFactory.getCompanyDao().getCompany(result.getLong("company_id"));
				
				computers.add(new Computer(
						result.getLong("id"),
						result.getString("name"),
						result.getDate("introduced"),
						result.getDate("discontinued"),
						company));
			}
			
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
			if(result.getString("company_id") != null)
				company = daoFactory.getCompanyDao().getCompany(result.getLong("company_id"));
			
			computer = new Computer(
					result.getLong("id"),
					result.getString("name"),
					result.getDate("introduced"),
					result.getDate("discontinued"),
					company);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computer;
	}

	@Override
	public void delete(Computer computer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Computer computer) {
		// TODO Auto-generated method stub
		
	}

}
