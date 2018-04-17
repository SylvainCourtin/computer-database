package com.excilys.computerdatabase.service;

import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class ServiceComputer implements Service {
	
	private ComputerDao computerDao;
	
	public ServiceComputer() {
		computerDao = DaoFactory.getInstance().getComputerDao();
	}

	
	public List<Computer> getComputers() {
		return computerDao.getList();
	}

	
	public boolean deleteComputer(Computer computer) {
		
		return computerDao.delete(computer);
	}

	
	public boolean addComputer(Computer computer) {
		return computerDao.add(computer);
	}
	
	public boolean addComputer(String name, Date dateIntroduced, Date dateDiscontinued, Company manufacturerCompany) throws DateDiscontinuedIntroducedException {
		return addComputer((new Computer(name, dateIntroduced, dateDiscontinued, manufacturerCompany)));
		
	}

	
	public boolean updateComputer(Computer oldComputer, Computer newComputer) {
		newComputer.setId(oldComputer.getId());
		return computerDao.update(newComputer);
	}
	
	public String getDetailsComputer(long id)
	{
		return computerDao.getComputer(id).toString();
	}

}
