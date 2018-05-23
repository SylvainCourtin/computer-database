package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
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
	
	@Autowired
	private ServiceComputer facade;
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	private MapperCompany mapperCompany;
	private Logger logger;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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
		else if(act.equals("validEdit"))
		{
			actionUpdateComputer(request, response);
		}
		else if(act.equals("delete"))
		{
			actionDeleteComputer(request, response);
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
		Optional<CompanyDTO> optCompany = mapperCompany.fromIdCompanyDTO(id_company);
		CompanyDTO company = null;
		if(optCompany.isPresent())
			company = optCompany.get();
		try 
		{
			if(request.getParameter("introduced") != null && !request.getParameter("introduced").equals(""))
				dateIntroduced = MyUtils.stringToDateInv(request.getParameter("introduced"));
			
			if(request.getParameter("discontinued") != null && !request.getParameter("discontinued").equals(""))
				dateDiscontinued = MyUtils.stringToDateInv(request.getParameter("discontinued"));
			
			
		} catch (DateTimeParseException e) {
			request.setAttribute("result", "Fail, "+e.getMessage());
			dispatchAddComputers(request, response);
			logger.debug("Wrong format date");
		}
		
		try 
		{
			if(facade.addComputer(name, dateIntroduced, dateDiscontinued, company) > 0)
			{
				logger.debug("Success added");
				request.setAttribute("result", "Success added.");
				//On renvoit l'utilisateur sur la page de la liste des computers
				dispatchGetComputers(request,response);
			}
			else
			{
				logger.debug("fail added");
				request.setAttribute("result", "Fail");
				dispatchAddComputers(request, response);
				
			}
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException | NoNameComputerException e) {
			if(e instanceof DateDiscontinuedIntroducedException)
				logger.debug("Invalid date, Introduced > Discontinued");
			else if(e instanceof CompanyDoesNotExistException)
				logger.debug("Company didn't exist");
			else
				logger.debug("The computer got an empty name");
			request.setAttribute("result", "Fail, "+e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
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
		int idComputer = Integer.valueOf(request.getParameter("id"));
		
		String name = request.getParameter("computerName");
		LocalDate dateIntroduced = null;
		LocalDate dateDiscontinued = null;
		int id_company = Integer.valueOf(request.getParameter("companyId"));
		Optional<CompanyDTO> optCompany = mapperCompany.fromIdCompanyDTO(id_company);
		CompanyDTO company = null;
		if(optCompany.isPresent())
			company = optCompany.get();
		try 
		{
			if(request.getParameter("introduced") != null && !request.getParameter("introduced").equals(""))
				dateIntroduced = MyUtils.stringToDateInv(request.getParameter("introduced"));
			
			if(request.getParameter("discontinued") != null && !request.getParameter("discontinued").equals(""))
				dateDiscontinued = MyUtils.stringToDateInv(request.getParameter("discontinued"));
			
			
		} catch (DateTimeParseException e) {
			request.setAttribute("result", "Fail. The date didn't match correctly");
			dispatchGetComputers(request, response);
			logger.debug("Wrong format date");
		}
		
		try 
		{
			if(facade.updateComputer(idComputer,name, dateIntroduced, dateDiscontinued, company))
			{
				logger.debug("Success updated");
				request.setAttribute("result", "Success updated.");
				//On renvoit l'utilisateur sur la page de la liste des computers
				dispatchGetComputers(request,response);
			}
			else
			{
				request.setAttribute("result", "Fail updated");
				dispatchGetComputers(request, response);
				
			}
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException | NoNameComputerException e) {
			if(e instanceof DateDiscontinuedIntroducedException)
				logger.debug("Invalid date, Introduced > Discontinued");
			else if(e instanceof CompanyDoesNotExistException)
				logger.debug("Company didn't exist");
			else
				logger.debug("The computer got an empty name");
			request.setAttribute("result", "Fail, "+e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
			
		}
	}
	
	/**
	 * Efface une liste de computer, les idées sont coté sous la forme "23,12,29,299, ..." chaque Id est séparé par une virgule
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void actionDeleteComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//id,id,id
		String stringIdComputerSeparateByComma = request.getParameter("selection");
		StringBuilder message = new StringBuilder("");
		short nbSuccess = 0;
		short nbFail = 0;
		
		try{
			List<String> listStringIdComputer = Arrays.asList(stringIdComputerSeparateByComma.split(","));
			for (String sId : listStringIdComputer) {
				
				int idComputer = Integer.parseInt(sId);
				

				if(facade.deleteComputer(idComputer))
					nbSuccess++;
				else
					nbFail++;
			}
			message.append("Computers deleted - ");
			//On affiche le message
			if(nbFail == 0)
			{
				message.append("Success : ");
				message.append(nbSuccess);
			}
			else
			{
				message.append("Fail : ");
				message.append(nbFail);
				message.append("\t");
				message.append("Success : ");
				message.append(nbSuccess);
			}
			request.setAttribute("result", message.toString());
			logger.debug("Success delete");
			dispatchGetComputers(request,response);
	
		}catch (Exception e) {
			logger.debug("Fail delete");
			request.setAttribute("result", "Error ! "+e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
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
			filter = request.getParameter("search").trim();
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
		for(Company company : serviceCompany.getCompanies(100, 0))
		{
			companies.add(mapperCompany.companyToDTO(company));
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
		if(request.getParameter("computer") != null)
		{
			int IdComputer = Integer.valueOf(request.getParameter("computer"));
			
			List<CompanyDTO> companies = new ArrayList<>();
			for(Company company :serviceCompany.getCompanies(100, 0))
			{
				companies.add(mapperCompany.companyToDTO(company));
			}
			
			Optional<ComputerDTO> computer = facade.getComputerDTO(IdComputer);
			if(computer.isPresent())
			{
				request.setAttribute("computer", computer.get());
				request.setAttribute("companies", companies);
				request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
			} else {
				request.setAttribute("result", "Error, you try to edit something has been deleted");
				request.getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
			}	
		}
		else
		{
			request.setAttribute("result", "Error, nothing was selected to being edit");
			request.getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		}
			
	}
}
