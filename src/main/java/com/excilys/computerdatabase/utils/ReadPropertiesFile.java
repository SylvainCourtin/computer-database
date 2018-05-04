package com.excilys.computerdatabase.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadPropertiesFile {
	
	private static final String FILE = "config.properties";
	private static final String PROPERTIES_SQL_URL = "mysql.url";
	private static final String PROPERTIES_SQL_USERNAME = "mysql.username";
	private static final String PROPERTIES_SQL_PASSWORD = "mysql.password";
	private static final String PROPERTIES_SQL_DRIVER = "mysql.driver";
	
	private static Logger logger = LoggerFactory.getLogger(ReadPropertiesFile.class);
	
	private String url;
	private String login;
	private String password;
	private String driver;
	
	public static ReadPropertiesFile getInstance()
	{
		return new ReadPropertiesFile();
	}
	
	public ReadPropertiesFile()
	{
		Properties properties = new Properties();

		try(InputStream output = ClassLoader.getSystemClassLoader().getResourceAsStream(FILE)) {
			properties.load(output);
			
			url = properties.getProperty(PROPERTIES_SQL_URL);
			login = properties.getProperty(PROPERTIES_SQL_USERNAME);
			password = properties.getProperty(PROPERTIES_SQL_PASSWORD);
			driver = properties.getProperty(PROPERTIES_SQL_DRIVER);
			
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
				
	}

	public String getUrl() {
		return url;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getDriver() {
		return driver;
	}
	
	
}
