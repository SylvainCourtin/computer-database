package com.excilys.computerdatabase.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.CompanyDaoImpl;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.ComputerDaoImpl;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.ReadPropertiesFile;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan
public class Application {
	
	@Bean("daoFactory")
	@Scope("singleton")
	public DaoFactory getDaoFactory()
	{
		return new DaoFactory();
	}
	
	@Bean("companyDao")
	@Scope("singleton")
	public CompanyDao getCompanyDao()
	{
		return new CompanyDaoImpl(getDaoFactory());
	}
	
	@Bean("computerDao")
	@Scope("singleton")
	public ComputerDao getComputerDao()
	{
		return new ComputerDaoImpl(getDaoFactory());
	}
	
	@Bean("serviceComputer")
	@Scope("singleton")
	public ServiceComputer getServiceComputer()
	{
		return new ServiceComputer(getComputerDao());
	}
	
	@Bean("serviceCompany")
	@Scope("singleton")
	public ServiceCompany getServiceCompany()
	{
		return new ServiceCompany(getCompanyDao());
	}
	@Bean
	public HikariDataSource getDataSource()
	{
		ReadPropertiesFile propertiesFile = ReadPropertiesFile.getInstance();
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(propertiesFile.getUrl());
		dataSource.setUsername(propertiesFile.getLogin());
		dataSource.setPassword(propertiesFile.getPassword());
		dataSource.setDriverClassName(propertiesFile.getDriver());
		
		return dataSource;
	}
}
