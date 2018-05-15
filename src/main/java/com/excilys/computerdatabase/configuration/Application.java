package com.excilys.computerdatabase.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Application {
	
	public static void main(String...args)
	{
		//https://www.journaldev.com/2637/spring-bean-life-cycle
		ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
		
	}
	
}
