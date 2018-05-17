package myTest.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;

import bddTest.MyBDDTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
@Configuration
public class DeleteCompany {
	
	private Logger logger;
	@Autowired
	private CompanyDao companyDao;
	
	@BeforeClass
	public static void initBDD() {
		MyBDDTest.getInstance().init();
		
	}
	
	@Before
	public void init()
	{
		logger = LoggerFactory.getLogger(getClass());
	}
	
	@AfterClass
	public static void destroyTest()
	{
		MyBDDTest.getInstance().destroy();
	}

	@Test
	public void testDeleteSuccess() {
		//On récupère l'id 1 qui est apple inc. dans notre bdd
		long id=1;
		assertThat(companyDao.getCompany(id).isPresent(), 
				is(true));
		
		try {
			assertThat(companyDao.deleteCompany(id),
					is(true));
		} catch (CompanyDoesNotExistException e) {
			logger.debug(e.getMessage());
			fail("Didn't expected the exception CompanyDoesNotExistException");
		}

		assertThat(companyDao.getCompany(id).isPresent(), 
				is(false));
	}
	
	@Test
	public void testDeleteFail() {

		long id=1700;//doesn't exist
		assertThat(companyDao.getCompany(id).isPresent(), 
				is(false));
		
		try {
			assertThat(companyDao.deleteCompany(id),
					is(false));
			fail("Expected exception");
		} catch (CompanyDoesNotExistException e) {
		}
	}

}
