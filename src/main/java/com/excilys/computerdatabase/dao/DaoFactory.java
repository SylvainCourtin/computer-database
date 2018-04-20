package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
	
	private static DaoFactory daoFactory;
	private static CompanyDao companyDao;
	private static ComputerDao computerDao;
	private String url;
	private String username;
	private String password;
	
	public DaoFactory(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public static DaoFactory getInstance() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {


        }

        if(daoFactory == null)
        {
        	DaoFactory instance = new DaoFactory(

                    "jdbc:mysql://localhost:3306/computer-database-db"
                    + "?serverTimezone=UTC"
                    + "&useSSL=true", 
                    "admincdb", "qwerty1234");

            return instance;
        }
        
        return daoFactory;
    }


    public Connection getConnection() throws SQLException {
    	
        return DriverManager.getConnection(url, username, password);

    }


    // Récupération du Dao

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
