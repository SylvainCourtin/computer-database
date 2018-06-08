package com.excilys.computerdatabase.dao;

import static org.hamcrest.CoreMatchers.*;
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
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class UpdateComputer {
	
	@Autowired
	private ComputerDao computerDao;
	
	@Test
	public void verifyBeans()
	{
		assertNotNull(computerDao);
	}
	
	@Test
	public void testUpdateComputer()
	{
		try {
			Computer computer = new Computer("ilMarche", DateUtil.stringToDate("null"), DateUtil.stringToDate("null"), null);
			long id;
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			//On fait les modifications
			computer.setName("OtherName");
			computer.setDateIntroduced(DateUtil.stringToDate("12-12-1999"));
			assertThat(computerDao.update(computer), 
					is(true));
			
			assertThat(computerDao.getComputer(id).get(), equalTo(computer));
			
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException | NoNameComputerException e) {
			fail("No exception expected");
		}
	}
	
	@Test
	public void testEchecChangeDateUpdateComputer()
	{
		try {
			Computer computer = new Computer("YouCantUpdateMe", DateUtil.stringToDate("12-12-1999"), DateUtil.stringToDate("null"), null);
			long id;
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			//On fait les modifications
			computer.setName("FailUpdateCauseDate");
			computer.setDateDiscontinued(DateUtil.stringToDate("12-12-1998"));
			assertThat(computerDao.update(computer), 
					is(false));
			
		} catch (DateTimeParseException | CompanyDoesNotExistException | NoNameComputerException e) {
			fail("No ParseException or CompanyDoesNotExistException exception expected");
		}catch(DateDiscontinuedIntroducedException e)
		{
			assert(true);
		}
	}

}
