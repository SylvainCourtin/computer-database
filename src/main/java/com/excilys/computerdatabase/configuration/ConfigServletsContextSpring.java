package com.excilys.computerdatabase.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ConfigServletsContextSpring implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		
	      AnnotationConfigWebApplicationContext rootContext =
	        new AnnotationConfigWebApplicationContext();
	      rootContext.register(Application.class);

	      container.addListener(new ContextLoaderListener(rootContext));
	      
	      rootContext.refresh();
	}

}
