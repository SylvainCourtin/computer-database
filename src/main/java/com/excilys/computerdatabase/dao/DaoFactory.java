package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.utils.ReadPropertiesFile;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class DaoFactory {
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ComputerDao computerDao;
	private HikariDataSource dataSource;
	
	public DaoFactory()
	{
		ReadPropertiesFile propertiesFile = ReadPropertiesFile.getInstance();
		dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(propertiesFile.getUrl());
		dataSource.setUsername(propertiesFile.getLogin());
		dataSource.setPassword(propertiesFile.getPassword());
		dataSource.setDriverClassName(propertiesFile.getDriver());	
	}


    public Connection getConnection() throws SQLException {
		return dataSource.getConnection();

    }
    
    public void closeConnection()
    {
    	dataSource.close();
    }


    /*------------------------------ Récupération du Dao ------------------------------ */

    public CompanyDao getCompanyDao() {
    	return companyDao;

    }
    
    public ComputerDao getComputerDao(){
    	return computerDao;
    }

}
