package com.excilys.computerdatabase.myTest.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.service.ServiceCompany;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
public class DeleteServiceCompany {

	private Logger logger = LoggerFactory.getLogger(getClass());;
	@Autowired
	private ServiceCompany serviceCompany;
	
	
	@Test
	public void verifyBeans()
	{
		assertNotNull(serviceCompany);
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
		}
	}

}
