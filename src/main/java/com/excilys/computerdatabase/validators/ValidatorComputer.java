package com.excilys.computerdatabase.validators;

import java.time.LocalDate;

import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.utils.MyUtils;

public class ValidatorComputer {
	
	/**
	 * Compare to date, Introduced must be before Discontinued
	 * @param introduced Date
	 * @param discontinued Date
	 * @throws DateDiscontinuedIntroducedException
	 */
	public static void dateDiscontinuedGreaterThanIntroduced(LocalDate introduced, LocalDate discontinued) throws DateDiscontinuedIntroducedException
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
}
