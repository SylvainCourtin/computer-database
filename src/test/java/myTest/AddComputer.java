package myTest;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.*;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.utils.MyUtils;

import bddTest.MyBDDTest;

public class AddComputer {
	
private ComputerDao computerDao;

 	private Logger logger;
	
	@Before 
	public void initBDD()
	{
		MyBDDTest.getInstance().init();
		computerDao = new DaoFactory("jdbc:mysql://localhost:3306/computer-database-db-test"
	            + "?serverTimezone=UTC"
	            + "&useSSL=true", 
	            "admincdb", "qwerty1234").getComputerDao();
		logger = LoggerFactory.getLogger(AddComputer.class);
	}
	
	@After
	public void destroyTest()
	{
		MyBDDTest.getInstance().destroy();
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
			
			assertThat(computerDao.add(new Computer("ilMarcheDate4", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), ServiceCompany.getInstance().getCompany(1))), 
					not(equalTo(-1L)));
			
		} catch (DateDiscontinuedIntroducedException | ParseException | CompanyDoesNotExistException e) {
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
			
			assertThat(computerDao.getComputer(id), equalTo(computer));
			
			//---------------------------Test avec une date----------------------------------------------------------
			
			computer = new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("null"), null);
			
			assertThat(computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(computerDao.getComputer(id), equalTo(computer));
			
			//------------------------------------------Test avec une company--------------------------------------------
			
			computer = new Computer("ilMarcheDate2", null, null, ServiceCompany.getInstance().getCompany(1));
			
			assertThat(computerDao.add(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(computerDao.getComputer(id), equalTo(computer));
			
		 }catch (DateDiscontinuedIntroducedException | ParseException | CompanyDoesNotExistException e) {
			 fail("No exception expected");
		}
	}
	
	@Test
	public void testEchecDateAddComputer()
	{
		try {
			Computer computer = new Computer("faildate", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("15-01-1999"), null);
			fail("Exception expected");
			assertThat(computerDao.add(computer), 
					equalTo(-1L));
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
					equalTo(-1L));
		} catch (DateDiscontinuedIntroducedException | ParseException | CompanyDoesNotExistException e) {
			fail("Not expected exception");
		}
	}
	
	@Test
	public void testEchecCompanyNotExistAddComputer()
	{
		try {
			assertThat(computerDao.add(new Computer("failCompany", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), new Company(1000, "marchepas"))), 
					equalTo(-1L));
		} catch (DateDiscontinuedIntroducedException | ParseException e) {
			fail("No exception DateDiscontinuedIntroducedException or ParseException expected");
		}
		catch( CompanyDoesNotExistException e)
		{
			assert(true);
		}

	}

}
