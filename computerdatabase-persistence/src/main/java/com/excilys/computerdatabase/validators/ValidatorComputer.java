package com.excilys.computerdatabase.validators;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.DateUtil;

@Component
public class ValidatorComputer {
	
	@Autowired
	private CompanyDao companyDao;
	
	/**
	 * Compare to date, Introduced must be before Discontinued
	 * @param introduced Date
	 * @param discontinued Date
	 * @throws DateDiscontinuedIntroducedException
	 */
	public void dateDiscontinuedGreaterThanIntroduced(LocalDate introduced, LocalDate discontinued) throws DateDiscontinuedIntroducedException
	{
		if(introduced != null && discontinued != null)
		{
			if(introduced.isAfter(discontinued))
				throw new DateDiscontinuedIntroducedException(
						"the date introduced("+DateUtil.formatDateToString(introduced) + ") "
						+ "must be before than " 
						+ "the date discontinued (" + DateUtil.formatDateToString(discontinued) +")");
		}
	}
	/**
	 * Check if this idCompany exist in the bdd
	 * @param idCompany
	 * @throws CompanyDoesNotExistException
	 */
	public void companyExist(long idCompany) throws CompanyDoesNotExistException
	{
		if(!companyDao.getCompany(idCompany).isPresent())
			throw new CompanyDoesNotExistException("The company "+idCompany + " doesn't exist !");
	}
	
	/**
	 * Check if this company exist in the bdd
	 * @param company
	 * @throws CompanyDoesNotExistException
	 */
	public void companyExist(Company company) throws CompanyDoesNotExistException
	{
		if(company != null)
			companyExist(company.getId());
	}
	
	/**
	 * Check if the computer is valid to be insert in the bdd
	 * @param computer
	 * @throws CompanyDoesNotExistException
	 * @throws DateDiscontinuedIntroducedException
	 * @throws NoNameComputerException
	 */
	public void validInsertComputer(Computer computer) throws CompanyDoesNotExistException, DateDiscontinuedIntroducedException, NoNameComputerException
	{
		if(computer.getName() != null && !computer.getName().trim().equals(""))
		{
			companyExist(computer.getManufacturerCompany());
			dateDiscontinuedGreaterThanIntroduced(computer.getDateIntroduced(), computer.getDateDiscontinued());
		}
		else
			throw new NoNameComputerException("No Name");
	}
}
