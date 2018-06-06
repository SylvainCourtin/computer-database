package com.excilys.computerdatabase.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConfigurationHibernate {
	
	private final SessionFactory sessionFactory;
	
	public ConfigurationHibernate() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
		sessionFactory.openSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
