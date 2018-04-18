package com.excilys.computerdatabase.mappers;

import com.excilys.computerdatabase.models.Company;

public class MapperCompany {

	public static Company fromParameters(long id, String name)
	{
		Company company = new Company(id,name);
		return company;
	}
}
