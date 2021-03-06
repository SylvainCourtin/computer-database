package com.excilys.computerdatabase.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = {
		"com.excilys.computerdatabase.dao", 
		"com.excilys.computerdatabase.validators"})
public class Application {
	@Bean("sessionFactory")
	@Scope
	public SessionFactory sessionFactory()
	{
		return new HibernateConfiguration().getSessionFactory();
	}
}