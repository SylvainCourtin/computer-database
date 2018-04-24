package com.excilys.computerdatabase.mappers;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.models.Company;

public class MapperCompany {

	public static Company fromParameters(long id, String name)
	{
		Company company = null;
		if(id > 0)
			company = new Company(id,name);
		return company;
	}
	
	public static CompanyDTO companyToDTO(Company company)
	{
		return new CompanyDTO(company);
	}
}
