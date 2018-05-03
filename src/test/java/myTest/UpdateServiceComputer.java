package myTest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.format.DateTimeParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyUtils;

import bddTest.MyBDDTest;

public class UpdateServiceComputer {
	
	private ServiceComputer serviceComputer;

	@Before 
	public void initBDD()
	{
		MyBDDTest.getInstance().init();
		serviceComputer = new ServiceComputer("jdbc:mysql://localhost:3306/computer-database-db-test"
	            + "?serverTimezone=UTC"
	            + "&useSSL=true", 
	            "admincdb", "qwerty1234");
	}
	
	@After
	public void destroyTest()
	{
		MyBDDTest.getInstance().destroy();
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
