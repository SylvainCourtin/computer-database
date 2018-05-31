package com.excilys.computerdatabase.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


class ConfigurationHibernate {
	
	private final SessionFactory factory;
	
	public ConfigurationHibernate() {
		factory = new Configuration().configure().buildSessionFactory();
		factory.openSession();
	}
	
	public SessionFactory getFactory() {
		return factory;
	}
}
