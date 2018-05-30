package com.excilys.computerdatabase.bddTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;


import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.excilys.computerdatabase.configuration.Application;

/**
 * Cette classe permet d'effectuer une reset de la bdd de test nommé  computer-database-db-test sur mysql
 * 
 * @author courtin
 *	Need : BDD mysql -> name = computer-database-db-test
 *	User with all privilige : admincdb, qwerty1234
 */
public class MyBDDTest {
    private static final String DROP_COMPUTER = "DROP TABLE computer";
    private static final String DROP_COMPANY = "DROP TABLE company";
    
    private ApplicationContext context = 
	          new AnnotationConfigApplicationContext(Application.class);
    private SessionFactory session = (SessionFactory) context.getBean("sessionFactory");
    
    private static MyBDDTest instance;
    
    public static MyBDDTest getInstance()
    {
    	 if(instance == null)
    		 instance = new MyBDDTest();
    	return instance;
    }
    
    /**
     * Initializes the Database with tables and entries
     */
    public void init() {
        String[] tablesStrings = transferDataFromFile("bdd/1-SCHEMA.sql");
        String[] entriesStrings = transferDataFromFile("bdd/3-ENTRIES.sql");
        
        executeScript(tablesStrings);
        executeScript(entriesStrings);
    }
    
    /**
     * Destroys the tables previously added
     */
    public void destroy() {
    	Transaction tx = session.getCurrentSession().beginTransaction();
    	try {
    		session.getCurrentSession().createQuery(DROP_COMPUTER).executeUpdate();
    		session.getCurrentSession().createQuery(DROP_COMPANY).executeUpdate();
    		tx.commit();   
       	}catch (Exception e) {
   			tx.rollback();
   			e.printStackTrace();
   		}
    }
    
    /**
     * Creates a String array representing a SQL file
     *
     * @param filename the path to the SQL file to be represented
     * @return String array representing a SQL file
     * @throws IOException
     */
    private String[] transferDataFromFile(String filename) {
        try (FileReader fileReader = new FileReader(getClass().getClassLoader().getResource(filename).getFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            
            String tempString = new String();
            StringBuilder stringBuilder = new StringBuilder();
            
            while ((tempString = bufferedReader.readLine()) != null)
                stringBuilder.append(tempString);
            
            bufferedReader.close();
            
            return stringBuilder.toString().split(";");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Executes several SQL statements and avoids executing empty statements
     *
     * @param sqlLines an array of String representing SQL statements
     * @param statement a {@link Statement} connected to the database executing the SQL queries
     * @throws SQLException
     */
    private void executeScript(String[] sqlLines) {
    	Transaction tx = session.getCurrentSession().beginTransaction();
    	try {
    		 for (int i = 0; i < sqlLines.length; i++)
    		 {
    			 if (!sqlLines[i].trim().equals("")) {
    				 session.getCurrentSession().createQuery(sqlLines[i]).executeUpdate();
 	            } 
    		 }
    	     tx.commit();      
    	}catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
       
    }
}
