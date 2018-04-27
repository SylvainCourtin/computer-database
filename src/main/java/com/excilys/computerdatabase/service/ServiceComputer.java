package com.excilys.computerdatabase.service;

import java.time.LocalDate;
import java.util.List;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.dtos.CompanyDTO;
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
	
	public long getNumberRowComputerLike(String sLike)
	{
		if(sLike != null && !sLike.equals(""))
			return computerDao.getNumberElementLike(sLike);
		else
			return computerDao.getNumberElement();
	}

	
	public List<Computer> getComputers(int limit, int offset) {
		return computerDao.getList(limit,offset);
	}
	
	public List<Computer> getComputers(int limit, int offset, String sLike) {
		if(sLike != null && !sLike.equals(""))
			return computerDao.getListLike(limit,offset,sLike);
		else
			return computerDao.getList(limit,offset);
	}

	
	public boolean deleteComputer(Computer computer) {
		return computerDao.delete(computer);
	}

	
	public long addComputer(Computer computer) throws CompanyDoesNotExistException {
		return computerDao.add(computer);
	}
	
	/**
	 * Ajoute un nouvelle ordinateur, dateIntroduced doit etre avant dateDiscontinued
	 * @param name not null
	 * @param dateIntroduced can be null
	 * @param dateDiscontinued can be null
	 * @param manufacturerCompany can be null
	 * @return if the adding is successfull
	 */
	public long addComputer(String name, LocalDate dateIntroduced, LocalDate dateDiscontinued, Company manufacturerCompany) throws DateDiscontinuedIntroducedException, CompanyDoesNotExistException {
		return addComputer((new Computer(name, dateIntroduced, dateDiscontinued, manufacturerCompany)));
		
	}
	
	/**
	 * Ajoute un nouvelle ordinateur, dateIntroduced doit etre avant dateDiscontinued
	 * @param name not null
	 * @param dateIntroduced can be null
	 * @param dateDiscontinued can be null
	 * @param manufacturerCompany is DTO (can be null
	 * @return if the adding is successfull
	 */
	public long addComputer(String name, LocalDate dateIntroduced, LocalDate dateDiscontinued, CompanyDTO manufacturerCompany) throws DateDiscontinuedIntroducedException, CompanyDoesNotExistException {
		Company company = null;
		if(manufacturerCompany != null)
			company = new Company(manufacturerCompany.getCompanyBasicView().getId(), manufacturerCompany.getCompanyBasicView().getName());
		return addComputer((new Computer(name, dateIntroduced, dateDiscontinued, company)));
		
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
