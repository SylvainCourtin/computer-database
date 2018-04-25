package com.excilys.computerdatabase.mappers;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;

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
	
	public static Company fromIdCompany(long id)
	{
		Company company = null;
		if(id > 0)
		{
			company = ServiceCompany.getInstance().getCompany(id);
		}
		return company;
	}
	
	public static CompanyDTO fromIdCompanyDTO(long id)
	{
		CompanyDTO companyDTO = null;
		if(id > 0)
		{
			Company company = ServiceCompany.getInstance().getCompany(id);
			if(company != null)
				companyDTO = companyToDTO(company);
		}
		return companyDTO;
	}
	
}
