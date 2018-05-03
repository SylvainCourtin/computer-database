package com.excilys.computerdatabase.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.validators.ValidatorComputer;

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
	
	/**
	 * Create service for a specific DB (like for the test)
	 * @param url
	 * @param username
	 * @param password
	 */
	public ServiceComputer(String url, String username, String password) {
		computerDao = new DaoFactory(url,username,password).getComputerDao();
	}
	
	/**
	 * Return the number of computer in the DB
	 * @return
	 */
	public long getNumberRowComputer()
	{
		return computerDao.getNumberElement();
	}
	
	/**
	 * Return the number of computer in the DB
	 * @param sLike How many computer got this name *sLike* 
	 * @return
	 */
	public long getNumberRowComputerLike(String sLike)
	{
		if(sLike != null && !sLike.equals(""))
			return computerDao.getNumberElementLike(sLike);
		else
			return computerDao.getNumberElement();
	}

	/**
	 * Return the list of computer 
	 * @param limit MaxComputer
	 * @param offset where start ? 
	 * @return list of computer
	 */
	public List<Computer> getComputers(int limit, int offset) {
		return computerDao.getList(limit,offset);
	}
	
	/**
	 * Return the list of computer with specific name 
	 * @param limit
	 * @param offset
	 * @param sLike name of computer like
	 * @return
	 */
	public List<Computer> getComputers(int limit, int offset, String sLike) {
		if(sLike != null && !sLike.equals(""))
			return computerDao.getListLike(limit,offset,sLike);
		else
			return computerDao.getList(limit,offset);
	}

	/**
	 * Efface grace à un computer déjà enregistrer possèdant un ID
	 * @param computer
	 * @return if success
	 */
	public boolean deleteComputer(Computer computer) {
		return computerDao.delete(computer);
	}
	
	/**
	 * Efface grace à un ID d'un computer existant deja dans la base
	 * @param id computer's id
	 * @return if success
	 */
	public boolean deleteComputer(long id) {
		return computerDao.delete(id);
	}

	/**
	 * 
	 * @param computer
	 * @return Id of this new computer
	 * @throws CompanyDoesNotExistException
	 * @throws DateDiscontinuedIntroducedException
	 */
	public long addComputer(Computer computer) throws CompanyDoesNotExistException, DateDiscontinuedIntroducedException {
		ValidatorComputer.dateDiscontinuedGreaterThanIntroduced(computer.getDateIntroduced(), computer.getDateDiscontinued());
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
	 * @throws DateDiscontinuedIntroducedException
	 * @throws CompanyDoesNotExistException
	 */
	public long addComputer(String name, LocalDate dateIntroduced, LocalDate dateDiscontinued, CompanyDTO manufacturerCompany) throws DateDiscontinuedIntroducedException, CompanyDoesNotExistException {
		Company company = null;
		if(manufacturerCompany != null)
			company = new Company(manufacturerCompany.getCompanyBasicView().getId(), manufacturerCompany.getCompanyBasicView().getName());
		return addComputer((new Computer(name, dateIntroduced, dateDiscontinued, company)));
		
	}
	/**
	 * 
	 * @param oldComputer
	 * @param newComputer
	 * @return
	 * @throws DateDiscontinuedIntroducedException
	 * @throws CompanyDoesNotExistException
	 */
	public boolean updateComputer(Computer oldComputer, Computer newComputer) throws DateDiscontinuedIntroducedException, CompanyDoesNotExistException {
		ValidatorComputer.dateDiscontinuedGreaterThanIntroduced(newComputer.getDateIntroduced(), newComputer.getDateDiscontinued());
		newComputer.setId(oldComputer.getId());
		return computerDao.update(newComputer);
	}
	
	/**
	 * Update un computer
	 * @param id must be in the BDD
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param manufacturerCompany
	 * @throws DateDiscontinuedIntroducedException
	 * @throws CompanyDoesNotExistException
	 * @return
	 */
	public boolean updateComputer(long id, String name, LocalDate introduced,LocalDate discontinued, CompanyDTO manufacturerCompany)throws DateDiscontinuedIntroducedException, CompanyDoesNotExistException {
		ValidatorComputer.dateDiscontinuedGreaterThanIntroduced(introduced, discontinued);
		Company company = null;
		if(manufacturerCompany != null)
			company = new Company(manufacturerCompany.getCompanyBasicView().getId(), manufacturerCompany.getCompanyBasicView().getName());
		return computerDao.update((new Computer(id,name, introduced, discontinued, company)));
	}
	
	
	/**
	 * Return the computer or NULL
	 * @param id
	 * @return
	 */
	public Optional<Computer> getComputer(long id)
	{
		return computerDao.getComputer(id);
	}
	
	public Optional<ComputerDTO> getComputerDTO(long id)
	{
		Optional<Computer> computer = computerDao.getComputer(id);
		if(computer.isPresent())
			return Optional.of(MapperComputer.computerToDTO(computer.get()));
		else
			return Optional.empty();
	}

}
