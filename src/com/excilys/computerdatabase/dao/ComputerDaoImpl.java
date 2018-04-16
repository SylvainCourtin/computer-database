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
		
		try {
			Connection connection = daoFactory.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(MyConstants.SQL_QUERY_COMPUTER_INSERT+'('+
					computer.getId()+','+
					computer.getName()+','+
					computer.getDateIntroduced()+','+
					computer.getDateDiscontinued()+','+
					computer.getManufacturerCompany().getIdCompany()+','+
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
				Company company = daoFactory.getCompanyDao().getCompany(result.getLong("company_id"));
				
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

}
