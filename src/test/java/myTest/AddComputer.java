package myTest;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.*;

import java.text.ParseException;

import org.junit.Test;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.utils.MyUtils;

public class AddComputer {
	
	private ComputerDao computerDao = new DaoFactory("jdbc:mysql://localhost:3306/computer-database-db-test"
                    + "?serverTimezone=UTC"
                    + "&useSSL=true", 
                    "admincdb", "qwerty1234").getComputerDao();
	
	@Test
	public void testAddComputer()
	{
		try {
			assertThat(computerDao.add(new Computer("ilMarche", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					is(true));
			
			assertThat(computerDao.add(new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("null"), null)), 
					is(true));
			
			assertThat(computerDao.add(new Computer("ilMarcheDate3", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("16-01-2001"), null)), 
					is(true));
			
			assertThat(computerDao.add(new Computer("ilMarcheDate4", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), ServiceCompany.getInstance().getCompany(1))), 
					is(true));
			
		} catch (DateDiscontinuedIntroducedException | ParseException | CompanyDoesNotExistException e) {
			fail("No exception expected");
		}
	}
	
	@Test
	public void testEchecDateAddComputer()
	{
		try {
			Computer computer = new Computer("ilMarche", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("15-01-1999"), null);
			fail("Exception expected");
			assertThat(computerDao.add(computer), 
					is(false));
		} catch (ParseException | CompanyDoesNotExistException e) {
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
			
			assertThat(computerDao.add(computer), 
					is(false));
		} catch (DateDiscontinuedIntroducedException | ParseException | CompanyDoesNotExistException e) {
			fail("Not expected exception");
		}
	}
	
	@Test
	public void testEchecCompanyNotExistAddComputer()
	{
		try {
			assertThat(computerDao.add(new Computer("ilMarche", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), new Company(1000, "marchepas"))), 
					is(false));
		} catch (DateDiscontinuedIntroducedException | ParseException e) {
			fail("No exception DateDiscontinuedIntroducedException or ParseException expected");
		}
		catch( CompanyDoesNotExistException e)
		{
			assert(true);
		}

	}

}
