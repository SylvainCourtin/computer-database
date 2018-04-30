package com.excilys.computerdatabase.dtos;

import java.time.LocalDate;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public class ComputerDTO {

	private ComputerBasicView computerBasicView;
	
	private CompanyBasicView companyBasicView;
	
	/**
	 * @param computer computer pour la vue
	 */
	public ComputerDTO(Computer computer) {
		computerBasicView = new ComputerBasicView(computer);
		
		companyBasicView = null;
		if(computer.getManufacturerCompany() != null)
		{
			companyBasicView = new CompanyBasicView(computer.getManufacturerCompany());
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
		companyBasicView = null;	
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
		companyBasicView = null;
		if(company != null)
		{
			companyBasicView = new CompanyBasicView(company);
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
	public void setCompanyBasicView(CompanyBasicView manufacturerCompanyBasicView) {
		this.companyBasicView = manufacturerCompanyBasicView;
	}

	/**
	 * @return the computerBasicView
	 */
	public ComputerBasicView getComputerBasicView() {
		return computerBasicView;
	}

	/**
	 * @return the manufacturerCompanyBasicView
	 */
	public CompanyBasicView getCompanyBasicView() {
		return companyBasicView;
	}
	
	

}
