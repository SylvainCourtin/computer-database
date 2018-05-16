package com.excilys.computerdatabase.mappers;

import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.computerdatabase.configuration.Application;
import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;

public class MapperCompany {
	
	private static ApplicationContext context = 
	          new AnnotationConfigApplicationContext(Application.class);
	private static ServiceCompany serviceCompany = (ServiceCompany) context.getBean("serviceCompany");;

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
	
	public static Optional<Company> fromIdCompany(long id)
	{
		if(id > 0)
		{
			return serviceCompany.getCompany(id);
		}
		return Optional.empty();
	}
	
	public static Optional<CompanyDTO> fromIdCompanyDTO(long id)
	{
		if(id > 0)
		{
			Optional<Company> company = serviceCompany.getCompany(id);
			if(company.isPresent())
				return Optional.ofNullable(companyToDTO(company.get()));
		}
		return Optional.empty();
	}
	
}
