package com.excilys.computerdatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class DashboardController {
	
	@RequestMapping(value = "dashboard", method = RequestMethod.POST)
	public String homePage(ModelMap model, @RequestParam("act") String act)
	{
		if(act != null)
		{
			if(act.equals("companies"))
			{
				return "redirect:/companies";
			}
			else if(act.equals("computers"))
			{
				return "redirect:/computer";
			}
			else if(act.equals("add"))
			{
				return "redirect:/computer/add";
			}
		}
		return RefPage.PAGE_INDEX;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String home()
	{
		return RefPage.PAGE_INDEX;
	}
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	public String homePage()
	{
		return RefPage.PAGE_INDEX;
	}
		
}
