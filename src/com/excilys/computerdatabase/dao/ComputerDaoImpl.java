package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.models.Computer;

public class ComputerDaoImpl implements ComputerDao {
	
	private DaoFactory daoFactory;

	public ComputerDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void add(Computer computer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Computer> getList() {
		// TODO Auto-generated method stub
		return null;
	}

}
