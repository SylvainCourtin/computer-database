package com.excilys.computerdatabase.start;

import java.util.Calendar;
import java.util.Date;

import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.Controller;

public class Main {
	
	public static void myTest()
	{
		Controller myController = new Controller();
		
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
			Computer c1 = new Computer("c1", cal1.getTime(), cal2.getTime(), comp1);
			Computer c2 = new Computer("c2", cal3.getTime(), cal4.getTime(), comp1);
			Computer c3 = new Computer("c3", cal5.getTime(), cal6.getTime(), comp2);
			Computer c4 = new Computer("c4", cal7.getTime(), cal8.getTime(), comp3);
			Computer c5 = new Computer("c5", cal9.getTime(), cal10.getTime(), comp3);
			
			myController.addComputer(c1);
			myController.addComputer(c2);
			myController.addComputer(c3);
			myController.addComputer(c4);
			myController.addComputer(c5);
			
			//Partie test
			
			for (Computer c : myController.getComputers()) {
				System.out.println(c.toString());
				
			}
			System.out.println("-------------------------------------");
			myController.deleteComputer(c1);
			
			for (Computer c : myController.getComputers()) {
				System.out.println(c.toString());
				
			}
			
			System.out.println("\n"+myController.getComputer(c2.getName()).equals(c2));
			
			
			
		} catch (DateDiscontinuedIntroducedException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		myTest();
		

	}

}
