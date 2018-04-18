package com.excilys.computerdatabase.mappers;

import java.util.Date;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class MapperComputer {
	
	public static Computer fromParameters(long id, String name, Date introduced, Date discontinued, Company company)
	{
		Computer computer = new Computer(id, name, introduced, discontinued,company);
		return computer;
	}

}
