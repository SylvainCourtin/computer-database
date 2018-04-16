package com.excilys.computerdatabase.dao;

import java.sql.Connection;

import com.excilys.computerdatabase.models.Computer;

public class ComputerDao extends Dao<Computer> {

	public ComputerDao(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Computer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Computer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Computer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Computer find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
