package com.excilys.computerdatabase.validators;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.utils.MyUtils;

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
						"the date introduced("+MyUtils.formatDateToString(introduced) + ") "
						+ "must be before than " 
						+ "the date discontinued (" + MyUtils.formatDateToString(discontinued) +")");
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
}
