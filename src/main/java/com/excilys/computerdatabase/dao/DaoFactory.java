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
	
	public DaoFactory(String url, String username, String password) {
		super();
		dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
	}
	
	public static DaoFactory getInstance() {
		
		ReadPropertiesFile propertiesFile = ReadPropertiesFile.getInstance();
		
        if(daoFactory == null)
        {
        	
        	DaoFactory instance = new DaoFactory(propertiesFile.getUrl(), propertiesFile.getLogin(), propertiesFile.getPassword());

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
    		return new CompanyDaoImpl(this);
    	else
    		return companyDao;

    }
    
    public ComputerDao getComputerDao()
    {
    	if(computerDao == null)
    		return new ComputerDaoImpl(this);
    	else
    		return computerDao;
    }

}
