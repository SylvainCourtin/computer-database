package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.service.ServiceCompany;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.MyConstants;

@Controller
@RequestMapping("/computer")
public class ComputerController {
	
	@Autowired
	private ServiceComputer facade;
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	private MapperCompany mapperCompany;
	@Autowired
	private MapperComputer mapperComputer;
	private Logger logger = LoggerFactory.getLogger(ComputerController.class);
	
	@GetMapping(value = "/edit", params= {"id"})
	public ModelAndView pageEdit(ModelMap model, @RequestParam("id") long idComputer)
	{
		logger.debug("call method actionEdit");
		return dispatchUpdateComputer( idComputer);
	}
	
	@PostMapping(value = "/edit")
	public ModelAndView pageEdit(RedirectAttributesModelMap redirectAttributesModelMap, @ModelAttribute("computer") ComputerDTO computerDTO, @RequestParam("id") long idComputer)
	{
		ModelAndView modelAndView = new ModelAndView(RefPage.PAGE_EDITCOMPUTER);
		return actionUpdateComputer(modelAndView, redirectAttributesModelMap, computerDTO, idComputer);
	}
	/**
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/add")
	public ModelAndView pageAdd(ModelMap model)
	{
		logger.debug("call method pageAdd");
		return dispatchAddComputers();
	}
	/**
	 * Adding the new computer
	 * @param model
	 * @param computerDTO
	 * @return
	 */
	@PostMapping(value = "/add")
	public ModelAndView pageAdd(RedirectAttributesModelMap redirectAttributesModelMap, @ModelAttribute("computer") ComputerDTO computerDTO)
	{
		ModelAndView modelAndView = new ModelAndView(RefPage.PAGE_ADDCOMPUTER);
		return actionAddComputer(modelAndView,redirectAttributesModelMap,computerDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getComputer(ModelMap model)
	{
		logger.debug("call method getComputer");
		return dispatchGetComputers(model, 0, null);
	}
	
	@RequestMapping(method = RequestMethod.GET, params= {"search"})
	public String getComputer(ModelMap model, @RequestParam("search") String search)
	{
		logger.debug("call method getComputer");
		return dispatchGetComputers(model, 0, search);
	}
	
	@RequestMapping(method = RequestMethod.GET, params= {"page", "search"})
	public String getComputer(ModelMap model, @RequestParam("page") int page, @RequestParam("search") String search)
	{
		logger.debug("call method getComputer");
		return dispatchGetComputers(model, page, search);
	}
	
	@GetMapping(value="*")
	public String redirect()
	{
		return "redirect:/dashboard";
	}
	
	/**
	 * @param modelAndView
	 * @param redirectAttributesModelMap
	 * @param computerDTO
	 * @return
	 */
	private ModelAndView actionAddComputer(ModelAndView modelAndView, RedirectAttributesModelMap redirectAttributesModelMap, ComputerDTO computerDTO)
	{		
		logger.debug("call method actionAddComputer");
		if(computerDTO.getCompanyBasicView().getId() == 0)
			computerDTO.setCompanyBasicView(null);
		try 
		{
			if(facade.addComputer(mapperComputer.fromParameters(computerDTO)) > 0)
			{
				logger.debug("Success added");
				redirectAttributesModelMap.addFlashAttribute("result", "Success added.");
				modelAndView.setViewName("redirect:/computer");
				return modelAndView;
			}
			else
			{
				logger.debug("fail added");
				redirectAttributesModelMap.addFlashAttribute("result", "Fail");
				modelAndView.setViewName("redirect:/computer");
				return modelAndView;
				
			}
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException | NoNameComputerException e) {
			if(e instanceof DateDiscontinuedIntroducedException)
				logger.debug("Invalid date, Introduced > Discontinued");
			else if(e instanceof CompanyDoesNotExistException)
				logger.debug("Company didn't exist");
			else
				logger.debug("The computer got an empty name");
			
			modelAndView.getModelMap().addAttribute("result", "Fail, "+e.getMessage());
			modelAndView.setViewName(RefPage.PAGE_500);
		}
		return modelAndView;
	}
	
	/**
	 * 
	 * @param modelAndView
	 * @param redirectAttributesModelMap
	 * @param computerDTO
	 * @param idComputer
	 * @return
	 */
	private ModelAndView actionUpdateComputer(ModelAndView modelAndView, RedirectAttributesModelMap redirectAttributesModelMap, ComputerDTO computerDTO, long idComputer)
	{
		logger.debug("call method actionUpdateComputer");
		Optional<CompanyDTO> optCompany = Optional.empty();
		if(computerDTO.getCompanyBasicView().getId() == 0)
			computerDTO.setCompanyBasicView(null);
		else
			optCompany = mapperCompany.fromIdCompanyDTO(computerDTO.getCompanyBasicView().getId());
		CompanyDTO company = null;
		if(optCompany.isPresent())
			company = optCompany.get();
		
		
		try 
		{
			if(facade.updateComputer(
					idComputer,
					computerDTO.getComputerBasicView().getName(), 
					computerDTO.getComputerBasicView().getIntroduced(), 
					computerDTO.getComputerBasicView().getDiscontinued(),
					company))
			{
				logger.debug("Success updated");
				redirectAttributesModelMap.addFlashAttribute("result", "Success Updated.");
				modelAndView.setViewName("redirect:/computer");
			}
			else
			{
				redirectAttributesModelMap.addFlashAttribute("result", "Fail updated");
				modelAndView.setViewName("redirect:/computer");	
			}
		} catch (DateDiscontinuedIntroducedException | CompanyDoesNotExistException | NoNameComputerException e) {
			if(e instanceof DateDiscontinuedIntroducedException)
				logger.debug("Invalid date, Introduced > Discontinued");
			else if(e instanceof CompanyDoesNotExistException)
				logger.debug("Company didn't exist");
			else
				logger.debug("The computer got an empty name");
			redirectAttributesModelMap.addFlashAttribute("result", "Fail, "+e.getMessage());
			modelAndView.setViewName(RefPage.PAGE_500);
		}
		return modelAndView;
	}	
	/**
	 * Efface une liste de computer, les idées sont coté sous la forme "23,12,29,299, ..." chaque Id est séparé par une virgule
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
//	protected void actionDeleteComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	{
//		String stringIdComputerSeparateByComma = request.getParameter("selection");
//		StringBuilder message = new StringBuilder("");
//		short nbSuccess = 0;
//		short nbFail = 0;
//		
//		try{
//			List<String> listStringIdComputer = Arrays.asList(stringIdComputerSeparateByComma.split(","));
//			for (String sId : listStringIdComputer) {
//				
//				int idComputer = Integer.parseInt(sId);
//				
//
//				if(facade.deleteComputer(idComputer))
//					nbSuccess++;
//				else
//					nbFail++;
//			}
//			message.append("Computers deleted - ");
//			//On affiche le message
//			if(nbFail == 0)
//			{
//				message.append("Success : ");
//				message.append(nbSuccess);
//			}
//			else
//			{
//				message.append("Fail : ");
//				message.append(nbFail);
//				message.append("\t");
//				message.append("Success : ");
//				message.append(nbSuccess);
//			}
//			request.setAttribute("result", message.toString());
//			logger.debug("Success delete");
//			dispatchGetComputers(request,response);
//	
//		}catch (Exception e) {
//			logger.debug("Fail delete");
//			request.setAttribute("result", "Error ! "+e.getMessage());
//			request.getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
//		}
//	}

	/* **************************************************************************
	 *
	 *        DISPATCH JSP
	 *
	 ****************************************************************************/
	/**
	 * Redirige vers l'affichage de la liste des computers sous format de page
	 * @param model
	 * @param nextPage
	 * @param search
	 * @return
	 */
	private String dispatchGetComputers(ModelMap model, int nextPage, String search)
	{
		String filter = "";
		if(search != null)
			filter = search.trim();
		long numberOfComputer = facade.getNumberRowComputerLike(filter);
		int res = 0;
		if(numberOfComputer%MyConstants.NUMBER_LIST_PER_PAGE > 0)
			res=1;
		int numberOfPages=(int) numberOfComputer/MyConstants.NUMBER_LIST_PER_PAGE + res;
		
		List<ComputerDTO> computers = new ArrayList<>();
		int page=1;
		//On récupere la page, si pas de numéro donné, on mets 0 par defaut
		try {
			if(nextPage > 0)
			{
				page = nextPage;
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
		model.addAttribute("computers", computers);
		model.addAttribute("page", page);
		model.addAttribute("numberOfPages", numberOfPages);
		model.addAttribute("numberOfComputer", numberOfComputer);
		model.addAttribute("search", filter);
		return RefPage.PAGE_LISTCOMPUTER;
		
	}
	
	/**
	 * Redirige vers une page formulaire ajouter un computer
	 * @param model
	 */
	private ModelAndView dispatchAddComputers()
	{
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		serviceCompany.getCompanies(100, 0).forEach(company -> companiesDTO.add(mapperCompany.companyToDTO(company)));
		
		//model.addAttribute("companies", companies);
		ModelAndView modelAndView =  new ModelAndView(RefPage.PAGE_ADDCOMPUTER, "computer", new ComputerDTO());
		modelAndView.addObject("companies", companiesDTO);
		return modelAndView;
	}
	
	/**
	 * Redirige vers une page formulaire pour mettre à jour un computer
	 */
	private ModelAndView dispatchUpdateComputer(long idComputer)
	{
		
		if(idComputer > 0)
		{				
			List<CompanyDTO> companiesDTO = new ArrayList<>();
			serviceCompany.getCompanies(100, 0).forEach(company -> companiesDTO.add(mapperCompany.companyToDTO(company)));
			
			Optional<ComputerDTO> computer = facade.getComputerDTO(idComputer);
			if(computer.isPresent())
			{
				ModelAndView modelAndView =  new ModelAndView(RefPage.PAGE_EDITCOMPUTER,"computer", new ComputerDTO());
				modelAndView.addObject("computer", computer.get());
				modelAndView.addObject("companies", companiesDTO);
				return modelAndView;
			} else {
				ModelAndView modelAndView =  new ModelAndView(RefPage.PAGE_500);
				modelAndView.addObject("result", "Error, you try to edit something has been deleted");
				return modelAndView;
			}	
		}
		else
		{
			ModelAndView modelAndView =  new ModelAndView(RefPage.PAGE_500);
			modelAndView.addObject("result", "Error, nothing was selected to being edit");
			return modelAndView;
		}
			
	}
}
