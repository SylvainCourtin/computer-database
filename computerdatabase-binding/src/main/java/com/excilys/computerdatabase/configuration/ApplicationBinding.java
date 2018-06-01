package com.excilys.computerdatabase.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;

@Configuration
@ComponentScan(basePackages = { "com.excilys.computerdatabase.mappers"})
public class ApplicationBinding {
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
