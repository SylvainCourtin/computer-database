package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.computerdatabase.utils.ReadPropertiesFile;
import com.zaxxer.hikari.HikariDataSource;

public class DaoFactory {
	
	private static DaoFactory daoFactory;
	private static CompanyDao companyDao;
	private static ComputerDao computerDao;
	private HikariDataSource dataSource;
	
	private DaoFactory(String url, String username, String password, String driver) {
		super();
		dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driver);
	}
	
	public static DaoFactory getInstance() {

        if(daoFactory == null)
        {
        	ReadPropertiesFile propertiesFile = ReadPropertiesFile.getInstance();
        	DaoFactory instance = new DaoFactory(propertiesFile.getUrl(), propertiesFile.getLogin(), propertiesFile.getPassword(), propertiesFile.getDriver());
            return instance;
        }
        
        return daoFactory;
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

    	if(companyDao == null)
    		companyDao = CompanyDaoImpl.getInstance(this);
    	return companyDao;

    }
    
    public ComputerDao getComputerDao()
    {
    	if(computerDao == null)
    		computerDao = ComputerDaoImpl.getInstance(this);
    	return computerDao;
    }

}
