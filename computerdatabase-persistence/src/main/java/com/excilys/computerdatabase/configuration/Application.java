package com.excilys.computerdatabase.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.CompanyDaoImpl;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.ComputerDaoImpl;
import com.excilys.computerdatabase.validators.ValidatorComputer;

@Configuration
@ComponentScan(basePackages = {
		"com.excilys.computerdatabase.dao", 
		"com.excilys.computerdatabase.validators"})
public class Application {
	
	@Bean("companyDao")
	@Scope("singleton")
	public CompanyDao getCompanyDao()
	{
		return new CompanyDaoImpl();
	}
	
	@Bean("computerDao")
	@Scope("singleton")
	public ComputerDao getComputerDao()
	{
		return new ComputerDaoImpl();
	}
	
	@Bean
	@Scope("singleton")
	public ConfigurationHibernate getConfigurationHibernate()
	{
		return new ConfigurationHibernate();
	}
	
	@Bean("sessionFactory")
	@Scope("singleton")
	public SessionFactory getSessionFactory()
	{
		return getConfigurationHibernate().getFactory();
	}
	
	@Bean
	@Scope("singleton")
	public ValidatorComputer getValidatorComputer()
	{
		return new ValidatorComputer();
	}
}