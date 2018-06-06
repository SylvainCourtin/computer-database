package com.excilys.computerdatabase.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "com.excilys.computerdatabase.mappers"})
@Import({Application.class})
public class ApplicationBinding {

}
