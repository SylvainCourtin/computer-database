package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.constant.SqlQuery;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.models.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	
	private Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
	private SessionFactory sessionFactory;
	
	public CompanyDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Company> getList(int limite, int offset) {
		List<Company> companies = new ArrayList<>();

		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			companies = session.createQuery(SqlQuery.SQL_QUERY_COMPANY_SELECT_ORDERBY, Company.class)
					.setMaxResults(limite)
					.setFirstResult(offset)
					.getResultList();
		}
		catch(HibernateException | NoResultException e)
		{
			logger.debug(e.getMessage());
		}	
		return companies;
	}
	
	@Override
	public Optional<Company> getCompany(long id)
	{
		Company company = null;
		
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			company = (Company) session.createQuery(SqlQuery.SQL_QUERY_COMPANY_SELECT_WHERE_ID, Company.class)
					.setParameter("idCompany", id)
					.getSingleResult();
		}
		catch(HibernateException | NoResultException e)
		{
			logger.debug(e.getMessage());
		}		
		return Optional.ofNullable(company);
	}

	@Override
	public long getNumberElement() {
		Long nb = 0L;		
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			nb = (Long) session.createQuery(SqlQuery.SQL_QUERY_COMPANY_COUNT).getSingleResult();
		}
		catch(HibernateException | NoResultException e)
		{
			logger.debug(e.getMessage());
		}		
		return nb;
	}
	
	@Override
	public boolean deleteCompany(long id) throws CompanyDoesNotExistException {
		boolean isDelete = false;
		if(getCompany(id).isPresent())
		{
			
			
			try(Session session = sessionFactory.getCurrentSession();) {
				session.beginTransaction();
				session.createQuery(SqlQuery.SQL_QUERY_COMPUTER_DELETE_RELATED_COMPANY)
				.setParameter("idCompany", id)
				.executeUpdate();
				
				session.createQuery(SqlQuery.SQL_QUERY_COMPANY_DELETE)
				.setParameter("idCompany", id)
				.executeUpdate();
				
				isDelete = true;
			}catch(HibernateException | NoResultException e)
			{
				logger.debug(e.getMessage());
			}	
		}
		else
			throw new CompanyDoesNotExistException();
		return isDelete;
	}
}
