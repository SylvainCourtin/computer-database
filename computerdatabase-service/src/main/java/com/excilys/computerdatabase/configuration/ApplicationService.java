package com.excilys.computerdatabase.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;

@Configuration
@ComponentScan(basePackages= {"com.excilys.computerdatabase.service"})
@Import({ApplicationBinding.class})
public class ApplicationService {
	
	@Autowired
	Application application;
	
	@Bean("serviceComputer")
	@Scope("singleton")
	public ServiceComputer getServiceComputer()
	{
		return new ServiceComputer(application.getComputerDao());
	}
	
	@Bean("serviceCompany")
	@Scope("singleton")
	public ServiceCompany getServiceCompany()
	{
		return new ServiceCompany(application.getCompanyDao());
	}
}
