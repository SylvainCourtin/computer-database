package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.excilys.computerdatabase.models.Computer;
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
		dispatchGetComputers(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("goToListComputers") != null)
			dispatchGetComputers(request,response);
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
		String name = request.getParameter("name");
		Date dateIntroduced = null;
		Date dateDiscontinued = null;
		int id_company = Integer.valueOf(request.getParameter("id_company"));
		CompanyDTO company = MapperCompany.fromIdCompanyDTO(id_company);
		try {
			dateDiscontinued = MyUtils.stringToDate(request.getParameter("discontinued"));
			dateIntroduced = MyUtils.stringToDate(request.getParameter("introduced"));
		} catch (ParseException e) {
			// TODO
			logger.debug("Wrong format date");
		}
		
		try {
			facade.addComputer(name, dateIntroduced, dateDiscontinued, company);
			logger.info("Success added");
			request.setAttribute("resultat", "Sucess.");
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException e) {
			// TODO Gestion erreur
			if(e instanceof DateDiscontinuedIntroducedException)
			{
				//TODO afficher combo de date non possible
				logger.debug("Invalid date, Introduced > Discontinued");
			}
			else
			{
				//TODO
				logger.debug("Company didn't exist");
			}
		}
		//On renvoit l'utilisateur sur la page de la liste des computers
		dispatchGetComputers(request,response);
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
		
	}
}
