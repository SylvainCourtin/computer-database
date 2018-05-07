package myTest.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.service.ServiceCompany;

import bddTest.MyBDDTest;

public class DeleteServiceCompany {

	private Logger logger;
	private static ServiceCompany serviceCompany;
	
	@BeforeClass
	public static void initBDD() {
		MyBDDTest.getInstance().init();
		serviceCompany = ServiceCompany.getInstance();
		
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
		assertThat(serviceCompany.getCompany(id).isPresent(), 
				is(true));
		
		try {
			assertThat(serviceCompany.deleteCompanyAndAllComputerRelatedToThisCompany(id),
					is(true));
		} catch (CompanyDoesNotExistException e) {
			logger.debug(e.getMessage());
			fail("Didn't expected the exception CompanyDoesNotExistException");
		}
		
		assertThat(serviceCompany.getCompany(id).isPresent(), 
				is(false));
	}
	
	@Test
	public void testDeleteFail() {
		//On récupère l'id 600 qui n'existe pas
		long id=600;
		assertThat(serviceCompany.getCompany(id).isPresent(), 
				is(false));
		
		try {
			assertThat(serviceCompany.deleteCompanyAndAllComputerRelatedToThisCompany(id),
					is(false));
			fail("Expected exception");
		} catch (CompanyDoesNotExistException e) {
			logger.debug(e.getMessage());
			assert(true);
		}
	}

}
