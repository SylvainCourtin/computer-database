package com.excilys.computerdatabase.myTest.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class DeleteComputer {
	
	@Autowired
	private ComputerDao computerDao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());;
	
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
	}

	@Test
	public void testDeleteComputer()
	{
		try {
			Computer computer = new Computer("ilMarche", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			long id;
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			assertThat(computerDao.delete(computer), 
					is(true));
			
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException | NoNameComputerException e) {
			fail("No exception expected");
			logger.debug("echec testDeleteComputer "+e.getMessage());
		}
	}
	
	@Test
	public void testEchecDeleteComputer()
	{
		try {
			Computer computer = new Computer(-1,"ilMarchePAS", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			assertThat(computerDao.delete(computer), 
					is(false));
			
		} catch (DateTimeParseException e) {
			fail("No exception expected");
		}
	}

}
