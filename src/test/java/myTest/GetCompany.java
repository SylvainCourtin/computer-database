package myTest;

import org.junit.Before;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.DaoFactory;

public class GetCompany {
	
private CompanyDao companyDao;
	
	@Before 
	public void initBDD()
	{
		companyDao = new DaoFactory("jdbc:mysql://localhost:3306/computer-database-db-test"
	            + "?serverTimezone=UTC"
	            + "&useSSL=true", 
	            "admincdb", "qwerty1234").getCompanyDao();
	}
	
}
