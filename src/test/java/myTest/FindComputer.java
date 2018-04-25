package myTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyUtils;

public class FindComputer {
	
private ComputerDao computerDao;
	
	@Before 
	public void initBDD()
	{
		computerDao = new DaoFactory("jdbc:mysql://localhost:3306/computer-database-db-test"
	            + "?serverTimezone=UTC"
	            + "&useSSL=true", 
	            "admincdb", "qwerty1234").getComputerDao();
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
			
			assertThat(computerDao.getList(1000, 0, "LEGRAND").size(), equalTo(4));
			
			
			
		} catch (DateDiscontinuedIntroducedException | ParseException | CompanyDoesNotExistException e) {
			fail("No exception expected");
		}
	}

}
