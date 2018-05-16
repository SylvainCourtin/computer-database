package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.utils.MyConstants;

@WebServlet("/companies")
public class ServletCompanies extends HttpServlet {
	
	@Autowired
	private ServiceCompany facade;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	/**
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dispatchGetCompanies(request,response);
	}
	
	/**
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
	
	/**
	 * Affiche la liste des companies sous format de page
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void dispatchGetCompanies(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		long numberOfCompanies = facade.getNumberRowComputer();

		int res = 0;
		if(numberOfCompanies%MyConstants.NUMBER_LIST_PER_PAGE > 0)
			res=1;
		int numberOfPages=(int) numberOfCompanies/MyConstants.NUMBER_LIST_PER_PAGE + res;
		
		List<CompanyDTO> companies = new ArrayList<>();
		int page=1;
		if(request.getParameter("page") != null && Integer.valueOf(request.getParameter("page")) > 0)
		{
			page = Integer.valueOf(request.getParameter("page"));
			//On redirige a la derniere page si le choix de la page dépasse le nombre de pages
			if(MyConstants.NUMBER_LIST_PER_PAGE*(page-1) > numberOfCompanies)
				page =  numberOfPages;
				
		}
		for(Company company : facade.getCompanies(MyConstants.NUMBER_LIST_PER_PAGE, MyConstants.NUMBER_LIST_PER_PAGE*(page-1)))
		{
			companies.add(MapperCompany.companyToDTO(company));
		}
		//Envoie des paramètres 
		request.setAttribute("companies", companies);
		request.setAttribute("page", page);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("numberOfCompanies", numberOfCompanies);
		request.getRequestDispatcher("/WEB-INF/views/listCompany.jsp").forward(request, response);
	}

}
