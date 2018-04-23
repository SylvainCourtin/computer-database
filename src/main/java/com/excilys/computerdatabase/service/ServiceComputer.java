package com.excilys.computerdatabase.service;

import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class ServiceComputer {
	
	private ComputerDao computerDao;
	
	private static ServiceComputer serviceComputer;
	
	public static ServiceComputer getInstance()
	{
		if(serviceComputer == null)
			return new ServiceComputer();
		else
			return serviceComputer;
	}
	
	public ServiceComputer() {
		computerDao = DaoFactory.getInstance().getComputerDao();
	}
	
	public long getNumberRowComputer()
	{
		return computerDao.getNumberElement();
	}

	
	public List<Computer> getComputers(int limit, int offset) {
		return computerDao.getList(limit,offset);
	}

	
	public boolean deleteComputer(Computer computer) {
		return computerDao.delete(computer);
	}

	
	public long addComputer(Computer computer) throws CompanyDoesNotExistException {
		return computerDao.add(computer);
	}
	
	/*
	 * Ajoute un nouvelle ordinateur, dateIntroduced doit etre avant dateDiscontinued
	 * @param name not null
	 * @param dateIntroduced can be null
	 * @param dateDiscontinued can be null
	 * @param manufacturerCompany can be null
	 * @return if the adding is successfull
	 */
	public long addComputer(String name, Date dateIntroduced, Date dateDiscontinued, Company manufacturerCompany) throws DateDiscontinuedIntroducedException, CompanyDoesNotExistException {
		return addComputer((new Computer(name, dateIntroduced, dateDiscontinued, manufacturerCompany)));
		
	}

	
	public boolean updateComputer(Computer oldComputer, Computer newComputer) {
		newComputer.setId(oldComputer.getId());
		return computerDao.update(newComputer);
	}
	
	public Computer getComputer(long id)
	{
		return computerDao.getComputer(id);
	}

}
