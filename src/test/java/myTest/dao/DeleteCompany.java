package myTest.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;

import bddTest.MyBDDTest;

public class DeleteCompany {
	
	private Logger logger;
	private static CompanyDao companyDao;
	
	@BeforeClass
	public static void initBDD() {
		MyBDDTest.getInstance().init();
		companyDao = DaoFactory.getInstance().getCompanyDao();
		
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
			assertThat(DaoFactory.getInstance().getComputerDao().deleteRelatedToCompany(id), 
					not(equalTo(-1L)));
		} catch (CompanyDoesNotExistException e) {
			logger.debug(e.getMessage());
			fail("Didn't expected the exception CompanyDoesNotExistException");
		}
		
		assertThat(companyDao.deleteCompany(id),
				is(true));
		
		assertThat(companyDao.getCompany(id).isPresent(), 
				is(false));
	}
	
	@Test
	public void testDeleteFail() {
		//On récupère l'id 17 qui est Sony dans notre bdd
		long id=17;
		assertThat(companyDao.getCompany(id).isPresent(), 
				is(true));
		
		assertThat(companyDao.deleteCompany(id),
				is(false));
		
		assertThat(companyDao.getCompany(id).isPresent(), 
				is(true));
	}

}
