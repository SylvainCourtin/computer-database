package myTest.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.format.DateTimeParseException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyUtils;

import bddTest.MyBDDTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class UpdateServiceComputer {
	
	@Autowired
	private ServiceComputer serviceComputer;

	@BeforeClass
	public static void initBDD()
	{
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
		assertNotNull(serviceComputer);
	}
	
	@Test
	public void testUpdateComputer()
	{
		try {
			Computer computer = new Computer("ilMarche", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			long id;
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			//On fait les modifications
			Computer oldComputer = computer;
			computer.setName("OtherName");
			computer.setDateIntroduced(MyUtils.stringToDate("12-12-1999"));
			assertThat(serviceComputer.updateComputer(oldComputer,computer), 
					is(true));
			
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException e) {
			fail("No exception expected");
		}
	}
	
	@Test
	public void testEchecChangeDateUpdateComputer()
	{
		try {
			Computer computer = new Computer("YouCantUpdateMe", MyUtils.stringToDate("12-12-1999"), MyUtils.stringToDate("null"), null);
			long id;
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			//On fait les modifications
			Computer oldComputer = computer;
			computer.setName("FailUpdateCauseDate");
			computer.setDateDiscontinued(MyUtils.stringToDate("12-12-1998"));
			assertThat(serviceComputer.updateComputer(oldComputer,computer), 
					is(false));
			fail("Exception DateDiscontinuedIntroducedException expecting");
		} catch (DateTimeParseException | CompanyDoesNotExistException e) {
			fail("No ParseException or CompanyDoesNotExistException exception expected");
		}catch(DateDiscontinuedIntroducedException e)
		{
			assert(true);
		}
	}
}
