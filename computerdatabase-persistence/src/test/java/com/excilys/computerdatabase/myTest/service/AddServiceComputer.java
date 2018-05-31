package com.excilys.computerdatabase.myTest.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.format.DateTimeParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class AddServiceComputer {
	
	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private ServiceCompany serviceCompany;
	
	@Test
	public void verifyBeans()
	{
		assertNotNull(serviceComputer);
		assertNotNull(serviceCompany);
	}
	
	/**
	 * Test add computer with Service
	 */
	@Test
	public void testAddComputer()
	{
		try {
			assertThat(serviceComputer.addComputer(new Computer("ilMarche", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(serviceComputer.addComputer(new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(serviceComputer.addComputer(new Computer("ilMarcheDate3", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("16-01-2001"), null)), 
					not(equalTo(-1L)));
			
			assertThat(serviceComputer.addComputer(new Computer("ilMarcheDate4", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), serviceCompany.getCompany(1).get())), 
					not(equalTo(-1L)));
			
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException | NoNameComputerException e) {
			fail("No exception expected");
		}
	}
	
	@Test
	public void testAddComputerGet()
	{
		 try{
			Computer computer = new Computer("compareMoi", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			long id;
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
						
			assertThat(serviceComputer.getComputer(id).get(), equalTo(computer));
			
			//---------------------------Test avec une date----------------------------------------------------------
			
			computer = new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"),null, null);
			
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(serviceComputer.getComputer(id).get(), equalTo(computer));
			
			//---------------------------Test avec deux date----------------------------------------------------------
			
			computer = new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("10-10-2017"), null);
			
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(serviceComputer.getComputer(id).get(), equalTo(computer));
			

			
			
			//------------------------------------------Test avec une company--------------------------------------------
			
			computer = new Computer("ilMarcheDate2", null, null, serviceCompany.getCompany(1).get());
			
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(serviceComputer.getComputer(id).get(), equalTo(computer));
			
		 }catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException | NoNameComputerException e) {
			 fail("No exception expected");
		}
	}
	
	@Test
	public void testEchecDateAddComputer()
	{
		try {
			Computer computer = new Computer("faildate", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("15-01-1999"), null);
			assertThat(serviceComputer.addComputer(computer), 
					equalTo(-1L));
			fail("Exception expected");
		} catch (DateTimeParseException | CompanyDoesNotExistException | NoNameComputerException e) {
			fail("ParseException or CompanyDoesNotExistException wasn't expected");
		}
		catch(DateDiscontinuedIntroducedException e){
			assert(true);
		}
	}
	
	@Test
	public void testEchecNoNameAddComputer()
	{
		try {
			Computer computer = new Computer(null, MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			assertThat(serviceComputer.addComputer(computer), 
					equalTo(-1L)); 
			fail("expected NoNameComputerException exception");
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException e) {
			fail("Not expected this exception");
		}
		catch (NoNameComputerException e) {
			assert(true);
		}
	}
	
	@Test
	public void testEchecCompanyNotExistAddComputer()
	{
		try {
			assertThat(serviceComputer.addComputer(new Computer("failCompany", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), new Company(1000, "marchepas"))), 
					equalTo(-1L));
			fail("expecting exception");
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | NoNameComputerException e) {
			fail("No exception DateDiscontinuedIntroducedException or ParseException expected");
		}
		catch( CompanyDoesNotExistException e)
		{
			assert(true);
		}
	}

}
