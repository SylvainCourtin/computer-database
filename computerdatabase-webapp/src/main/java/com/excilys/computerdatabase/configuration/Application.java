package com.excilys.computerdatabase.configuration;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
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
import com.excilys.computerdatabase.validators.ValidatorComputer;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.excilys.computerdatabase.dao", 
		"com.excilys.computerdatabase.mappers", 
		"com.excilys.computerdatabase.service", 
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
	
	@Bean("dataSource")
	@Scope("singleton")
	public DataSource getDataSource()
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
	public JdbcTemplate getJdbcTemplate(DataSource dataSource)
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
	
	@Bean
	@Scope("singleton")
	public ValidatorComputer getValidatorComputer()
	{
		return new ValidatorComputer();
	}
	
	@Bean("sessionFactory")
	@Scope("singleton")
	public SessionFactory getSessionFactory()
	{
		return getConfigurationHibernate().getFactory();
	}
	
	@Bean
	@Scope("singleton")
	public ConfigurationHibernate getConfigurationHibernate()
	{
		return new ConfigurationHibernate();
	}
}
