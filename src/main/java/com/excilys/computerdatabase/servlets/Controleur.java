package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;

@WebServlet("/")
public class Controleur extends HttpServlet {
	
	private ServiceCompany facade;
	
	private static final long serialVersionUID = 1L;

	public Controleur() {
		super();
		facade = ServiceCompany.getInstance();
	}
	
	protected void actionGetCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		long numberOfCompanies = facade.getNumberRowComputer();
		List<CompanyDTO> companies = new ArrayList<>();
		for(Company company : facade.getCompanies(50, 0))
		{
			companies.add(MapperCompany.companyToDTO(company));
		}
		request.setAttribute("companies", companies);
		request.setAttribute("numberOfCompanies", numberOfCompanies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/listCompany.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		actionGetCompany(request,response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

}
