package com.excilys.computerdatabase.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class HibernateDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession()
	{
		if(sessionFactory.isOpen())
			return sessionFactory.getCurrentSession();
		else
			return sessionFactory.openSession();
	}
}
