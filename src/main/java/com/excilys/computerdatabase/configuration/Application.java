package com.excilys.computerdatabase.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.CompanyDaoImpl;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.ComputerDaoImpl;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.ReadPropertiesFile;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan
public class Application {
	
	@Bean("companyDao")
	@Scope("singleton")
	public CompanyDao getCompanyDao()
	{
		return new CompanyDaoImpl(getDataSource(getDataSource()));
	}
	
	@Bean("computerDao")
	@Scope("singleton")
	public ComputerDao getComputerDao()
	{
		return new ComputerDaoImpl(getDataSource(getDataSource()));
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
	@Scope("singleton")
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
	
	@Bean
	@Scope("singleton")
	public JdbcTemplate getDataSource(DataSource dataSource)
	{
		return new JdbcTemplate(dataSource);		
	}
	
	@Bean
	@Scope("singleton")
	public MapperCompany getMapperCompany()
	{
		return new MapperCompany();		
	}
	
	@Bean
	@Scope("singleton")
	public MapperComputer getMapperComputer()
	{
		return new MapperComputer();		
	}
}
