package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.utils.MyConstants;

@Controller
@RequestMapping("/companies")
public class CompanyController {
	
	private ServiceCompany facadeCompany;
	private MapperCompany mapperCompany;
	
	public CompanyController(ServiceCompany facadeCompany, MapperCompany mapperCompany) {
		super();
		this.facadeCompany = facadeCompany;
		this.mapperCompany = mapperCompany;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getCompanies(ModelMap model)
	{
		return dispatchGetCompanies(model, 0);
	}
	
	@RequestMapping(method = RequestMethod.GET, params="page")
	public String getCompanies(ModelMap model, @RequestParam("page") int page)
	{
		return dispatchGetCompanies(model, page);
	}
	
	/**
	 * Affiche la liste des companies sous format de page
	 */
	private String dispatchGetCompanies(ModelMap model, int page)
	{
		long numberOfCompanies = facadeCompany.getNumberRowComputer();

		int res = 0;
		if(numberOfCompanies%MyConstants.NUMBER_LIST_PER_PAGE > 0)
			res=1;
		int numberOfPages=(int) numberOfCompanies/MyConstants.NUMBER_LIST_PER_PAGE + res;
		
		List<CompanyDTO> companies = new ArrayList<>();
		int newPage = 1;
		if(page > 0)
		{
			newPage=page;
			//On redirige a la derniere page si le choix de la page dépasse le nombre de pages
			if(MyConstants.NUMBER_LIST_PER_PAGE*(newPage-1) > numberOfCompanies)
				newPage = numberOfPages;
				
		}
		for(Company company : facadeCompany.getCompanies(MyConstants.NUMBER_LIST_PER_PAGE, MyConstants.NUMBER_LIST_PER_PAGE*(newPage-1)))
		{
			companies.add(mapperCompany.companyToDTO(company));
		}
		//Envoie des paramètres 
		model.addAttribute("companies", companies);
		model.addAttribute("page", newPage);
		model.addAttribute("numberOfPages", numberOfPages);
		model.addAttribute("numberOfCompanies", numberOfCompanies);		
		return RefPage.PAGE_LISTCOMPANY;
	}
	
	

}
