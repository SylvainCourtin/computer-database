package myTest.dao;

import static org.hamcrest.CoreMatchers.*;
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

public class DeleteComputer {
	
	private ComputerDao computerDao;
	
	@Before 
	public void initBDD()
	{
		MyBDDTest.getInstance().init();
		computerDao = DaoFactory.getInstance().getComputerDao();
	}
	
	@After
	public void destroyTest()
	{
		MyBDDTest.getInstance().destroy();
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
			
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException e) {
			fail("No exception expected");
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
