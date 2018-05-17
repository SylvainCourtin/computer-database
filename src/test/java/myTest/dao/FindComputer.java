package myTest.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyUtils;

import bddTest.MyBDDTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
@Configuration
public class FindComputer {
	
	@Autowired
	private ComputerDao computerDao;
	@Autowired
	private ServiceComputer serviceComputer;
	
	@BeforeClass
	public static void initBDD()
	{
		MyBDDTest.getInstance().init();		
	}
	
	@AfterClass
	public static void destroyTest()
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
			
			
			
		} catch (DateDiscontinuedIntroducedException | DateTimeParseException | CompanyDoesNotExistException e) {
			fail("No exception expected");
		}
	}
	
	/**
	 * Récupère le computer à l'id 5 existant nativement dans la BDD, il doit avoir une date introduced mais pas de discontinued
	 */
	@Test
	public void testFindWithDate()
	{
		//Computer = id:5, name:CM-5, introduced:01-01-1991, discontinued:null, company_id:2
		Optional<Computer> computer = computerDao.getComputer(5);
		if(computer.isPresent())
		{
			assertThat(computer.get().getDateIntroduced(), not(equalTo(null)));
			assertThat(computer.get().getDateDiscontinued(), equalTo(null));
		}
		else
		{
			fail();
		}

		
	}
	
	/**
	 * Ici on vérifie que si la recherche sur Service avec like = null nous renvoit bien le nombre d'élément de la bdd sans le like
	 */
	@Test
	public void testServiceFind()
	{
		assertEquals(serviceComputer.getNumberRowComputerLike(null), serviceComputer.getNumberRowComputer());
	}
	
	/**
	 * Ici on vérifie que si la recherche sur Service avec like avec un name qui exite
	 * Par exemple : commodore
	 *  nous renvoie un nombre différent que sans 
	 */
	@Test
	public void testServiceFindNotEquals()
	{
		assertNotEquals(serviceComputer.getNumberRowComputerLike("commodore"), serviceComputer.getNumberRowComputer());
	}
	
	/**
	 * On vérifie qu'il existe dans notre BDD de test bien 13 commodore
	 * Ceci vérifie aussi avec la sensibilité à la 'case'
	 */
	@Test
	public void testFindNumberOfCommodore()
	{
		String lowercaseCommodore = "commodore";
		String upperCaseCommodore = "Commodore";
		//Check case
		assertEquals(computerDao.getNumberElementLike(upperCaseCommodore), computerDao.getNumberElementLike(lowercaseCommodore));
		
		//On vérifie le nombre
		assertEquals(computerDao.getNumberElementLike(upperCaseCommodore), 13L);
	}
	
	/**
	 * Test si avec une limite de 10 on récupère bien 10 élément
	 */
	@Test
	public void testGetSomething()
	{
		assertEquals(computerDao.getList(10, 0).size(), 10);
	}

}
