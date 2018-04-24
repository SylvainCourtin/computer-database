package com.excilys.computerdatabase.dtos;

import java.time.LocalDate;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class ComputerDTO {

	private ComputerBasicView computerBasicView;
	
	private CompanyBasicView manufacturerCompanyBasicView;
	
	/**
	 * @param computer computer pour la vue
	 */
	public ComputerDTO(Computer computer) {
		computerBasicView = new ComputerBasicView(computer);
		
		manufacturerCompanyBasicView = null;
		if(computer.getManufacturerCompany() != null)
		{
			manufacturerCompanyBasicView = new CompanyBasicView(computer.getManufacturerCompany());
		}
	}
	
	/**
	 * Constructeur avec des paramètres spécifiques (cas ou dates et company sont NULL)
	 * @param id id du computer
	 * @param name nom du computer
	 */
	public ComputerDTO(long id, String name)
	{
		computerBasicView = new ComputerBasicView(id,name,null,null);
		manufacturerCompanyBasicView = null;	
	}
	
	/**
	 * Constructeur avec des paramètres spécifiques
	 * @param id id du computer
	 * @param name nom du computer
	 * @param introduced Can be null
	 * @param discontinued Can be null
	 * @param company Can be null
	 */
	public ComputerDTO(long id, String name,LocalDate introduced, LocalDate discontinued, Company company)
	{
		computerBasicView = new ComputerBasicView(id,name,introduced,discontinued);
		manufacturerCompanyBasicView = null;
		if(company != null)
		{
			manufacturerCompanyBasicView = new CompanyBasicView(company);
		}
	}

	/**
	 * @param computerBasicView the computerBasicView to set
	 */
	public void setComputerBasicView(ComputerBasicView computerBasicView) {
		this.computerBasicView = computerBasicView;
	}

	/**
	 * @param manufacturerCompanyBasicView the manufacturerCompanyBasicView to set
	 */
	public void setManufacturerCompanyBasicView(CompanyBasicView manufacturerCompanyBasicView) {
		this.manufacturerCompanyBasicView = manufacturerCompanyBasicView;
	}
	
	

}
