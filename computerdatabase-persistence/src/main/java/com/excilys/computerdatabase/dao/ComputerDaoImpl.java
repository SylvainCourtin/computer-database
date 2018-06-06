package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.constant.SqlQuery;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.validators.ValidatorComputer;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	
	private Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	private ValidatorComputer validatorComputer;
	
	private SessionFactory sessionFactory;
	
	public ComputerDaoImpl(SessionFactory sessionFactory, ValidatorComputer validatorComputer) {
		this.sessionFactory = sessionFactory;
		this.validatorComputer = validatorComputer;
	}

	@Override
	public long add(Computer computer) throws CompanyDoesNotExistException, DateDiscontinuedIntroducedException, NoNameComputerException{
		long id = -1;
		validatorComputer.validInsertComputer(computer);
		
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			id = (Long) session.save(computer);
		}catch (HibernateException e) {
			logger.debug("error, rollback");
		}
		return id;
	}

	@Override
	public List<Computer> getList(int limite, int offset) {
		List<Computer> computers = new ArrayList<>();
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			computers = session.createQuery(SqlQuery.SQL_QUERY_COMPUTER_SELECT_ORDERBY, Computer.class)
					.setMaxResults(limite)
					.setFirstResult(offset)
					.getResultList();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
		}
		return computers;
	}
	
	@Override
	public List<Computer> getListLike(int limite, int offset, String sLike) {
		List<Computer> computers = new ArrayList<>();
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			computers = session.createQuery(SqlQuery.SQL_QUERY_COMPUTER_SELECT_LIKE_ORDERBY, Computer.class)
				.setMaxResults(limite)
				.setFirstResult(offset)
				.setParameter("like","%"+sLike+"%")
				.getResultList();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
		}
		return computers;
	}

	@Override
	public Optional<Computer> getComputer(long id) {
		Computer computer = null;
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			computer = session.get(Computer.class, id);
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
		}
		return Optional.ofNullable(computer);
	}

	@Override
	public boolean delete(Computer computer) {
		return delete(computer.getId());
	}
	
	@Override
	public boolean delete(long id)
	{
		boolean isDelete = false;
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			isDelete = session.createQuery(SqlQuery.SQL_QUERY_COMPUTER_DELETE)
					.setParameter("idComputer",id)
					.executeUpdate() > 0;
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
		}		
		return isDelete;
	}

	@Override
	public boolean update(Computer computer) throws CompanyDoesNotExistException, DateDiscontinuedIntroducedException, NoNameComputerException {
		boolean isUpdate = false;
		validatorComputer.validInsertComputer(computer);
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			session.saveOrUpdate(computer); //throws HibernateException
			isUpdate = true;
		}catch (HibernateException e) {
			isUpdate = false;
			logger.debug(e.getMessage());
		}
		return isUpdate;
		
	}

	@Override
	public long getNumberElement() {
		long nb = 0;
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			nb = (Long) session.createQuery(SqlQuery.SQL_QUERY_COMPUTER_COUNT).getSingleResult();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
		}
		return nb;
	}

	@Override
	public long getNumberElementLike(String sLike) {
		long nb = 0;
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			nb = (Long) session.createQuery(SqlQuery.SQL_QUERY_COMPUTER_COUNT_LIKE)
					.setParameter("like", "%"+sLike+"%")
					.getSingleResult();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
		}
		return nb;
	}
	
	@Override
	public long getNumberComputerRelatedToThisCompany(long idCompany) {
		long nb = 0;
		try(Session session = sessionFactory.getCurrentSession();) {
			session.beginTransaction();
			nb = (Long) session.createQuery(SqlQuery.SQL_QUERY_COMPUTER_COUNT_RELATED_COMPANY)
					.setParameter("idCompany", idCompany)
					.getSingleResult();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
		}
		return nb;
	}
}
