package com.excilys.computerdatabase.myTest.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.*;

import java.time.format.DateTimeParseException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.bddTest.MyBDDTest;
import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class AddComputer {
	
	@Autowired
	private ComputerDao computerDao;
	@Autowired
	private CompanyDao companyDao;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@BeforeClass
	public static void initBDD() {
		MyBDDTest.getInstance().init();
		
	}
	
	@AfterClass
	public static void destroyTest()
	{
		MyBDDTest.getInstance().destroy();
		
	}
	
	@Test
	public void verifyBeans()
	{
		assertNotNull(computerDao);
		assertNotNull(companyDao);
	}
	
	@Test
	public void testAddComputer()
	{
		try {
			assertThat(computerDao.add(new Computer("ilMarche", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(computerDao.add(new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(computerDao.add(new Computer("ilMarcheDate3", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("16-01-2001"), null)), 
					not(equalTo(-1L)));
			
			assertThat(computerDao.add(new Computer("ilMarcheDate4", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), companyDao.getCompany(1).get())), 
					not(equalTo(-1L)));
			
		} catch (DateTimeParseException | CompanyDoesNotExistException | DateDiscontinuedIntroducedException | NoNameComputerException e) {
			fail("No exception expected");
		}
	}
	
	@Test
	public void testAddComputerGet()
	{
		 try{
			Computer computer = new Computer("compareMoi", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			long id;
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			logger.debug("Id :"+id);
			
			assertThat(computerDao.getComputer(id).get(), equalTo(computer));
			
			//---------------------------Test avec une date----------------------------------------------------------
			
			computer = new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"),null, null);
			
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(computerDao.getComputer(id).get(), equalTo(computer));
			
			//---------------------------Test avec deux date----------------------------------------------------------
			
			computer = new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("10-10-2017"), null);
			
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(computerDao.getComputer(id).get(), equalTo(computer));
			

			
			
			//------------------------------------------Test avec une company--------------------------------------------
			
			computer = new Computer("ilMarcheDate2", null, null, companyDao.getCompany(1).get());
			
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(computerDao.getComputer(id).get(), equalTo(computer));
			
		 }catch (DateTimeParseException | CompanyDoesNotExistException | DateDiscontinuedIntroducedException | NoNameComputerException e) {
			 fail("No exception expected");
		}
	}
	
	@Test
	public void testEchecDateAddComputer()
	{
		try {
			Computer computer = new Computer("faildate", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("15-01-1999"), null);
			assertThat(computerDao.add(computer), 
					equalTo(-1L));
			fail("Exception expected");
			
		} catch (DateTimeParseException | CompanyDoesNotExistException | NoNameComputerException e) {
			fail("ParseException wasn't expected");
		} catch (DateDiscontinuedIntroducedException e) {
			assert(true);
		}
		
	}
	
	@Test
	public void testEchecNoNameAddComputer()
	{
		try {
			Computer computer = new Computer(null, MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			
			assertThat(computerDao.add(computer), 
					equalTo(-1L));
		} catch (DateTimeParseException | DateDiscontinuedIntroducedException | CompanyDoesNotExistException e) {
			fail("Not expected this exception");
		} catch (NoNameComputerException e) {
			assert(true);
		}
	}
	
	@Test
	public void testEchecCompanyNotExistAddComputer()
	{
		try {
			assertThat(computerDao.add(new Computer("failCompany", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), new Company(1000, "marchepas"))), 
					equalTo(-1L));
		} catch (DateTimeParseException | DateDiscontinuedIntroducedException | NoNameComputerException e) {
			fail("No exception DateDiscontinuedIntroducedException or ParseException expected");
		} catch(CompanyDoesNotExistException e)
		{
			assert(true);
		}
	}

}
