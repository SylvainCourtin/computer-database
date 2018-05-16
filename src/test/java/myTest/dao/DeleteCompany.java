package myTest.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;

import bddTest.MyBDDTest;

public class DeleteCompany {
	
	private Logger logger;
	private static CompanyDao companyDao;
	private static ApplicationContext context;
	
	@BeforeClass
	public static void initBDD() {
		MyBDDTest.getInstance().init();
		context = 
		          new AnnotationConfigApplicationContext(Application.class);
		companyDao = (CompanyDao) context.getBean("companyDao");
		
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
