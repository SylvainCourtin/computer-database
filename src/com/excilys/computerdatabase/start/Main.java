package com.excilys.computerdatabase.start;

import java.util.Calendar;
import java.util.Date;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceComputer;

public class Main {
	
	public static void testDao()
	{
		Company comp1 = new Company("Asus");
		Company comp2 = new Company("Acer");
		Company comp3 = new Company("Apple");
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2017,  10, 10);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2018, 10,10);
		Calendar cal3 = Calendar.getInstance();
		cal3.set(2016,  10, 10);
		Calendar cal4 = Calendar.getInstance();
		cal4.set(2018, 1,10);
		Calendar cal5 = Calendar.getInstance();
		cal5.set(2015,  2, 27);
		Calendar cal6 = Calendar.getInstance();
		cal6.set(2015,  12, 24);
		Calendar cal7 = Calendar.getInstance();
		cal7.set(2010, 10,10);
		Calendar cal8 = Calendar.getInstance();
		cal8.set(2011,  10, 10);
		Calendar cal9 = Calendar.getInstance();
		cal9.set(2018, 2,4);
		Calendar cal10 = Calendar.getInstance();
		cal10.set(2018, 4,1);
		
		
		try {
			
			
			CompanyDao companyDao = DaoFactory.getInstance().getCompanyDao();
			ComputerDao computerDao =DaoFactory.getInstance().getComputerDao();
			
			Computer c1 = new Computer("c1", cal1.getTime(), cal2.getTime(), null);
			Computer c2 = new Computer("c2", cal3.getTime(), cal4.getTime(), comp1);
			Computer c3 = new Computer("c3", cal5.getTime(), cal6.getTime(), comp2);
			Computer c4 = new Computer("c4", cal7.getTime(), cal8.getTime(), comp3);
			Computer c5 = new Computer("c5", cal9.getTime(), cal10.getTime(), comp3);
			
			//testGetDaoCompany(companyDao);
			//testGetDaoComputer(computerDao);
			//testGetIDDaoComputer(computerDao,7); // -> company null
			//testGetIDDaoComputer(computerDao,2); // -> company Thinking Machines
			//testGetIDDaoCompany(companyDao,3);
			testAddCompany(companyDao, comp1);
			
			testAddComputer(computerDao,c2);
			
		}
		catch (DateDiscontinuedIntroducedException e) {
			e.printStackTrace();
		}
	}
	
	public static void testGetDaoCompany(CompanyDao companyDao)
	{
		for (Company c : companyDao.getList()) {
			System.out.println(c.toString());
		}
	}
	public static void testGetDaoComputer(ComputerDao computerDao)
	{
		for (Computer c : computerDao.getList()) {
			System.out.println(c.toString());
		}
	}
	
	public static void testGetIDDaoComputer(ComputerDao computerDao, long id)
	{
		System.out.println(computerDao.getComputer(id).toString());
	}
	
	public static void testGetIDDaoCompany(CompanyDao companyDao, long id)
	{
		System.out.println(companyDao.getCompany(id).toString());
	}
	
	public static void testAddCompany(CompanyDao companyDao, Company c)
	{
		companyDao.add(c);

	}
	
	public static void testAddComputer(ComputerDao computerDao, Computer c)
	{
		computerDao.add(c);
		testGetDaoComputer(computerDao);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//myTest();
		testDao();

	}

}
;