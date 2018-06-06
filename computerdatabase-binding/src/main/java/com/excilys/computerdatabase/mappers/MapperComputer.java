package com.excilys.computerdatabase.mappers;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

@Component
public class MapperComputer{
	
	private MapperCompany mapperCompany;

	public MapperComputer(MapperCompany mapperCompany) {
		super();
		this.mapperCompany = mapperCompany;
	}
	
	public static Computer fromParameters(long id, String name, LocalDate introduced, LocalDate discontinued, Company company)
	{
		return new Computer(id, name, introduced, discontinued,company);
	}
	public Computer fromParameters(ComputerDTO computerDTO)
	{
		Company company = computerDTO.getCompanyBasicView() == null ? null :
				mapperCompany.fromParameters(
				computerDTO.getCompanyBasicView().getId(), 
				computerDTO.getCompanyBasicView().getName());
		
		return fromParameters(computerDTO.getComputerBasicView().getId(), 
				computerDTO.getComputerBasicView().getName(), 
				computerDTO.getComputerBasicView().getIntroduced(), 
				computerDTO.getComputerBasicView().getDiscontinued(),
				company);
	}
	
	public static ComputerDTO computerToDTO(Computer computer)
	{
		return new ComputerDTO(computer);
	}

	/**
	 * Create a new computer, the type date is convert to LocalDate
	 * @param id Id giving by the BDD
	 * @param name 
	 * @param introduced Date will be convert to LocalDate
	 * @param discontinued Date will be convert to LocalDate
	 * @param company The company, can be null
	 * @return
	 */
	public static Computer fromParameters(long id, String name, Date introduced, Date discontinued, Company company) {
		//Convertie la date en localDate si non null
		LocalDate localDateIntroduced = (introduced == null ? null : introduced.toLocalDate());
		LocalDate localDateDiscontinued = (discontinued == null ? null : discontinued.toLocalDate());
		return new Computer(id, name, localDateIntroduced, localDateDiscontinued,company);
	}
	
	/**
	 * Create a new computer, the type date is convert to LocalDate
	 * @param id Id giving by the BDD
	 * @param name 
	 * @param introduced String from BDD 
	 * @param discontinued String from BDD 
	 * @param company The company, can be null
	 * @return
	 */
	public static Computer fromParameters(long id, String name, String introduced, String discontinued, Company company) {
		//Convertie la date en localDate si non null
		LocalDate localDateIntroduced = (introduced == null ? null : LocalDate.parse(introduced));
		LocalDate localDateDiscontinued = (discontinued == null ? null : LocalDate.parse(discontinued));
		return new Computer(id, name, localDateIntroduced, localDateDiscontinued,company);
	}
}
