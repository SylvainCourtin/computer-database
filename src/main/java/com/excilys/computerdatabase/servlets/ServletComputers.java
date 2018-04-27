package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyConstants;
import com.excilys.computerdatabase.utils.MyUtils;

/**
 * Servlet implementation class ServletsComputer
 */
@WebServlet("/computer")
public class ServletComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServiceComputer facade;
	private Logger logger;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletComputers() {
        super();
        facade = ServiceComputer.getInstance();
        logger = LoggerFactory.getLogger(ServletComputers.class);
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
			dispatchGetComputers(request, response);
		}
		else if(act.equals("add"))
		{
			dispatchAddComputers(request, response);
		}
		else if(act.equals("valideAdd"))
		{
			actionAddComputer(request, response);
		}
		else if(act.equals("edit"))
		{
			dispatchUpdateComputer(request, response);
		}
		else
		{
			dispatchGetComputers(request, response);
		}
		
	}
	
	/* **************************************************************************
	 *
	 *        ACTION JSP
	 *
	 ****************************************************************************/
	
	
	/**
	 * Action effectuant l'ajout d'un nouveau computer
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void actionAddComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		String name = request.getParameter("computerName");
		LocalDate dateIntroduced = null;
		LocalDate dateDiscontinued = null;
		int id_company = Integer.valueOf(request.getParameter("companyId"));
		CompanyDTO company = MapperCompany.fromIdCompanyDTO(id_company);
		try 
		{
			if(request.getParameter("discontinued") != null && request.getParameter("introduced").equals(""))
				dateDiscontinued = MyUtils.stringToDateInv(request.getParameter("discontinued"));
			if(request.getParameter("introduced") != null && request.getParameter("introduced").equals(""))
				dateIntroduced = MyUtils.stringToDateInv(request.getParameter("introduced"));
		} catch (DateTimeParseException e) {
			// TODO
			logger.debug("Wrong format date");
		}
		
		try 
		{
			if(facade.addComputer(name, dateIntroduced, dateDiscontinued, company) > 0)
			{
				logger.info("Success added");
				request.setAttribute("result", "Success added.");
				//On renvoit l'utilisateur sur la page de la liste des computers
				dispatchGetComputers(request,response);
			}
			else
			{
				request.setAttribute("result", "Fail. You forget the name, didn't you ?");
				dispatchAddComputers(request, response);
				
			}
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException e) {
			// TODO Gestion erreur
			if(e instanceof DateDiscontinuedIntroducedException)
			{
				//TODO afficher combo de date non possible
				logger.debug("Invalid date, Introduced > Discontinued");
			}
			else
			{
				logger.debug("Company didn't exist");
			}
			request.setAttribute("result", "Fail, "+e.getMessage());
			dispatchAddComputers(request, response);
			
		}
		
	}
	
	/**
	 * Met à jour un computer
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void actionUpdateComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
	}
	
	/**
	 * Efface un computer
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void actionDeleteComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
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
		long numberOfComputer = facade.getNumberRowComputerLike(filter);
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
		
		for(Computer computer : facade.getComputers(MyConstants.NUMBER_LIST_PER_PAGE, MyConstants.NUMBER_LIST_PER_PAGE*(page-1), filter))
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
	 * Redirige vers une page formulaire ajouter un computer
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void dispatchAddComputers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<CompanyDTO> companies = new ArrayList<>();
		for(Company company : ServiceCompany.getInstance().getCompanies(100, 0))
		{
			companies.add(MapperCompany.companyToDTO(company));
		}
		
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}
	
	/**
	 * Redirige vers une page formulaire pour mettre à jour un computer
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException servlet exception
	 * @throws IOException io exception
	 */
	protected void dispatchUpdateComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		ComputerDTO computer = (ComputerDTO) request.getAttribute("computer");
		
		List<CompanyDTO> companies = new ArrayList<>();
		for(Company company : ServiceCompany.getInstance().getCompanies(100, 0))
		{
			companies.add(MapperCompany.companyToDTO(company));
		}
		
		request.setAttribute("computer", computer);
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}
}
