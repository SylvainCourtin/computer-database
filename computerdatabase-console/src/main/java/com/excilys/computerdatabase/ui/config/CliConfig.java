package com.excilys.computerdatabase.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.excilys.computerdatabase.dao", 
		"com.excilys.computerdatabase.mappers", 
		"com.excilys.computerdatabase.service", 
		"com.excilys.computerdatabase.validators"})
public class CliConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
