package com.excilys.computerdatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DashboardController {
	
	private final static String FILE_VIEWS= "views/";
	private final static String PAGE_403 = "403";
	private final static String PAGE_404 = "404";
	private final static String PAGE_500 = "500";
	private final static String PAGE_INDEX = "index";
	private final static String PAGE_ADDCOMPUTER = "addComputer";
	private final static String PAGE_EDITCOMPUTER = "editComputer";
	private final static String PAGE_LISTCOMPANY = "listCompany";
	private final static String PAGE_LISTCOMPUTER = "listComputer";
	
	@RequestMapping(method = RequestMethod.POST)
	public String defaultpage(ModelMap model)
	{
		return homePage(model);
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
	public String homePage(ModelMap model)
	{
		String action = (String) model.get("act");
		return FILE_VIEWS+PAGE_LISTCOMPUTER;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String home()
	{
		return PAGE_INDEX;
	}
}
