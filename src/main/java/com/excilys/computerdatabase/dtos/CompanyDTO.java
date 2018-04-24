package com.excilys.computerdatabase.dtos;

import com.excilys.computerdatabase.models.Company;

public class CompanyDTO {
	
	private CompanyBasicView companyBasicView;

	/**
	 * Transforme en DTO 
	 * @param company company correspondant
	 */
	public CompanyDTO(Company company) {
		companyBasicView = new CompanyBasicView(company);
	}
	
	/**
	 * Transforme en DTO via un id et un nom
	 * @param id id de la company
	 * @param name nom de la company
	 */
	public CompanyDTO(long id, String name)
	{
		companyBasicView = new CompanyBasicView(id,name);
	}
	
	
	/**
	 * @return retourne la view de la company
	 */
	public CompanyBasicView getCompanyBasicView() {
		return companyBasicView;
	}
}
