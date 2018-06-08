package com.excilys.computerdatabase.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	@ExceptionHandler(Throwable.class)
	public ModelAndView handleThrowable(Throwable e)
	{
		logger.debug(e.getLocalizedMessage());
		logger.debug(e.getMessage());
		Arrays.stream(e.getStackTrace()).forEach(printrace -> logger.debug(printrace.toString()));

		ModelAndView modelAndView = new ModelAndView(RefPage.PAGE_500);
		modelAndView.addObject("result", e.getMessage());
		return modelAndView;
	}
	
	@ExceptionHandler({NoNameComputerException.class, DateDiscontinuedIntroducedException.class, CompanyDoesNotExistException.class})
	public ModelAndView handleExceptionRequest(Exception e)
	{
		if(e instanceof DateDiscontinuedIntroducedException)
			logger.debug("Invalid date, Introduced > Discontinued");
		else if(e instanceof CompanyDoesNotExistException)
			logger.debug("Company didn't exist");
		else
			logger.debug("The computer got an empty name");
		ModelAndView modelAndView = new ModelAndView(RefPage.PAGE_400);
		modelAndView.addObject("result", "Fail, "+e.getMessage());
		return modelAndView;
	}
}
