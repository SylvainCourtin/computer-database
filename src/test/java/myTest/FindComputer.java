package myTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.text.ParseException;

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

public class FindComputer {
	
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

	/**
	 * Test qui vérifie après l'ajout de ces 4 computer avec le nom contenant LEGRAND on trouve le m^eme nombre
	 */
	@Test
	public void testFindLike() {
		try {
			assertThat(computerDao.add(new Computer("LEGRAND v1 la version 1", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(computerDao.add(new Computer("LEGRAND___ v2", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(computerDao.add(new Computer("___LEGRAND v3", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(computerDao.add(new Computer("LEGRAND___v4", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			String likeName = "LEGRAND";
			//On vérifie que le nombre mis en BDD correspond bien 
			assertThat(computerDao.getListLike(1000, 0, likeName).size(), equalTo(4));
			//getNumberElement return a LONG
			assertThat(computerDao.getNumberElementLike(likeName), equalTo(4L));
			
			
			
		} catch (DateDiscontinuedIntroducedException | ParseException | CompanyDoesNotExistException e) {
			fail("No exception expected");
		}
	}

}
