package myTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.format.DateTimeParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyUtils;

import bddTest.MyBDDTest;

public class AddServiceComputer {
	
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
	
	/**
	 * Test add computer with Service
	 */
	@Test
	public void testAddComputer()
	{
		try {
			assertThat(serviceComputer.addComputer(new Computer("ilMarche", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(serviceComputer.addComputer(new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("null"), null)), 
					not(equalTo(-1L)));
			
			assertThat(serviceComputer.addComputer(new Computer("ilMarcheDate3", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("16-01-2001"), null)), 
					not(equalTo(-1L)));
			
			assertThat(serviceComputer.addComputer(new Computer("ilMarcheDate4", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), ServiceCompany.getInstance().getCompany(1).get())), 
					not(equalTo(-1L)));
			
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException e) {
			fail("No exception expected");
		}
	}
	
	@Test
	public void testAddComputerGet()
	{
		 try{
			Computer computer = new Computer("compareMoi", MyUtils.stringToDate("null"), MyUtils.stringToDate("null"), null);
			long id;
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
						
			assertThat(serviceComputer.getComputer(id), equalTo(computer));
			
			//---------------------------Test avec une date----------------------------------------------------------
			
			computer = new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"),null, null);
			
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(serviceComputer.getComputer(id), equalTo(computer));
			
			//---------------------------Test avec deux date----------------------------------------------------------
			
			computer = new Computer("ilMarcheDate2", MyUtils.stringToDate("16-01-2000"), MyUtils.stringToDate("10-10-2017"), null);
			
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(serviceComputer.getComputer(id), equalTo(computer));
			

			
			
			//------------------------------------------Test avec une company--------------------------------------------
			
			computer = new Computer("ilMarcheDate2", null, null, ServiceCompany.getInstance().getCompany(1).get());
			
			assertThat(id = serviceComputer.addComputer(computer), 
					not(equalTo(-1L)));
			
			computer.setId(id);
			
			assertThat(serviceComputer.getComputer(id), equalTo(computer));
			
		 }catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException e) {
			 fail("No exception expected");
		}
	}

}
