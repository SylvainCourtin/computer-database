package com.excilys.computerdatabase.mappers;

import java.sql.Date;
import java.time.LocalDate;

import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class MapperComputer {
	
	public static Computer fromParameters(long id, String name, LocalDate introduced, LocalDate discontinued, Company company)
	{
		Computer computer = new Computer(id, name, introduced, discontinued,company);
		return computer;
	}
	
	public static ComputerDTO computerToDTO(Computer computer)
	{
		return new ComputerDTO(computer);
	}

	/**
	 * Create a new computer, the type date is convert to LocalDate
	 * @param id Id giving by the BDD
	 * @param name 
	 * @param introduced Date wil be convert to LocalDate
	 * @param discontinued Date wil be convert to LocalDate
	 * @param company The company, can be null
	 * @return
	 */
	public static Computer fromParameters(long id, String name, Date introduced, Date discontinued, Company company) {
		LocalDate localDateIntroduced = null;
		LocalDate localDateDiscontinued = null;
		if(introduced != null)
			localDateIntroduced = introduced.toLocalDate();
		if(discontinued != null)
			localDateDiscontinued = discontinued.toLocalDate();
		Computer computer = new Computer(id, name, localDateIntroduced, localDateDiscontinued,company);
		return computer;
	}

}
