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
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyConstants;

/**
 * Servlet implementation class dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServiceCompany facadeCompany;
	@Autowired
	private ServiceComputer facadeComputer;
	
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doIt(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doIt(request, response);
	}
	
	/**
	 * Permet d'avoir une sorte de menu en fonction des appeles du parametres act
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		
		if(act == null)
		{
			this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		}
		else if(act.equals("companies"))
		{
			dispatchGetCompanies(request, response);
			//this.getServletContext().getRequestDispatcher("/computer").forward(request, response);
		}
		else if(act.equals("computers"))
		{
			dispatchGetComputers(request, response);
			//this.getServletContext().getRequestDispatcher("/companies").forward(request, response);
		}
		else if(act.equals("add"))
		{
			dispatchAddComputers(request, response);
		}
		
	}
	
	/* **************************************************************************
	 *
	 *        DISPATCH JSP
	 *
	 ****************************************************************************/
	/**
	 * Redirige vers l'affichage de la liste des computers sous format de page
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void dispatchGetComputers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String filter = "";
		if(request.getParameter("search") != null)
			filter = request.getParameter("search");
		long numberOfComputer = facadeComputer.getNumberRowComputerLike(filter);
		int res = 0;
		if(numberOfComputer%MyConstants.NUMBER_LIST_PER_PAGE > 0)
			res=1;
		int numberOfPages=(int) numberOfComputer/MyConstants.NUMBER_LIST_PER_PAGE + res;
		
		List<ComputerDTO> computers = new ArrayList<>();
		int page=1;
		//On récupere la page, si pas de numéro donné, on mets 0 par defaut
		try {
			if(request.getParameter("page") != null && Integer.valueOf(request.getParameter("page")) > 0)
			{
				page = Integer.valueOf(request.getParameter("page"));
				//On redirige a la derniere page si le choix de la page dépasse le nombre de pages
				if(MyConstants.NUMBER_LIST_PER_PAGE*(page-1) > numberOfComputer)
					page =  numberOfPages;
					
			}
		}catch (NumberFormatException e) {
		}
		
		for(Computer computer : facadeComputer.getComputers(MyConstants.NUMBER_LIST_PER_PAGE, MyConstants.NUMBER_LIST_PER_PAGE*(page-1), filter))
		{
			computers.add(MapperComputer.computerToDTO(computer));
		}
		//Envoie des paramètres 
		request.setAttribute("computers", computers);
		request.setAttribute("page", page);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("numberOfComputer", numberOfComputer);
		request.setAttribute("search", filter);
		request.getRequestDispatcher("/WEB-INF/views/listComputer.jsp").forward(request, response);
		
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
		long numberOfCompanies = facadeCompany.getNumberRowComputer();

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
		for(Company company : facadeCompany.getCompanies(MyConstants.NUMBER_LIST_PER_PAGE, MyConstants.NUMBER_LIST_PER_PAGE*(page-1)))
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
	
	/**
	 * Redirige vers une page formulaire ajouter un computer
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void dispatchAddComputers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<CompanyDTO> companies = new ArrayList<>();
		for(Company company : facadeCompany.getCompanies(100, 0))
		{
			companies.add(MapperCompany.companyToDTO(company));
		}
		
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

}
