package myTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.format.DateTimeParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyUtils;

import bddTest.MyBDDTest;

public class UpdateComputer {
	
	private ComputerDao computerDao;
	
	@Before 
	public void initBDD()
	{
		MyBDDTest.getInstance().init();
		computerDao = new DaoFactory("jdbc:mysql://localhost:3306/computer-database-db-test"
	            + "?serverTimezone=UTC"
	            + "&useSSL=true", 
	            "admincdb", "qwerty1234").getComputerDao();
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
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			//On fait les modifications
			computer.setName("OtherName");
			computer.setDateIntroduced(MyUtils.stringToDate("12-12-1999"));
			assertThat(computerDao.update(computer), 
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
			assertThat(id = computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			//On fait les modifications
			computer.setName("FailUpdateCauseDate");
			computer.setDateDiscontinued(MyUtils.stringToDate("12-12-1998"));
			assertThat(computerDao.update(computer), 
					is(false));
			
		} catch (DateTimeParseException | CompanyDoesNotExistException e) {
			fail("No ParseException or CompanyDoesNotExistException exception expected");
		}catch(DateDiscontinuedIntroducedException e)
		{
			assert(true);
		}
	}

}
