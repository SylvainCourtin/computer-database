package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.exception.DateDiscontinuedIntroducedException;
import com.excilys.computerdatabase.exception.NoNameComputerException;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyConstants;
import com.excilys.computerdatabase.validators.ValidatorComputer;

@Repository
public class ComputerDaoImpl extends HibernateDAO implements ComputerDao {
	
	private Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	private ValidatorComputer validatorComputer;
	
	public ComputerDaoImpl() {
		super();
	}

	@Override
	public long add(Computer computer) throws CompanyDoesNotExistException, DateDiscontinuedIntroducedException, NoNameComputerException{
		long id = -1;
		validatorComputer.validInsertComputer(computer);
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			id = (Long) session.save(computer); //throws HibernateException
			tx.commit();
		}catch (HibernateException e) {
			logger.debug("error, rollback");
			tx.rollback();
		}
		return id;
	}

	@Override
	public List<Computer> getList(int limite, int offset) {
		List<Computer> computers = new ArrayList<>();
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			computers = session.createQuery(MyConstants.SQL_QUERY_COMPUTER_SELECT_ORDERBY, Computer.class)
					.setMaxResults(limite)
					.setFirstResult(offset)
					.getResultList();
			tx.commit();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
			tx.rollback();
		}
		return computers;
	}
	
	@Override
	public List<Computer> getListLike(int limite, int offset, String sLike) {
		List<Computer> computers = new ArrayList<>();
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			computers = session.createQuery(MyConstants.SQL_QUERY_COMPUTER_SELECT_ORDERBY, Computer.class)
					.setMaxResults(limite)
					.setFirstResult(offset)
					.setParameter("like","%"+sLike+"%")
					.getResultList();
			tx.commit();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
			tx.rollback();
		}
		return computers;
	}

	@Override
	public Optional<Computer> getComputer(long id) {
		Computer computer = null;
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			computer = session.get(Computer.class, id);
			tx.commit();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
			tx.rollback();
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
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			isDelete = session.createQuery(MyConstants.SQL_QUERY_COMPUTER_DELETE)
					.setParameter("idComputer",id)
					.executeUpdate() > 0;
			tx.commit();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
			tx.rollback();
		}		
		return isDelete;
	}

	@Override
	public boolean update(Computer computer) throws CompanyDoesNotExistException, DateDiscontinuedIntroducedException, NoNameComputerException {
		boolean isUpdate = false;
		validatorComputer.validInsertComputer(computer);
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(computer); //throws HibernateException
			tx.commit();
			isUpdate = true;
		}catch (HibernateException e) {
			isUpdate = false;
			logger.debug(e.getMessage());
			tx.rollback();
		}
		return isUpdate;
		
	}

	@Override
	public long getNumberElement() {
		long nb = 0;
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			nb = (Long) session.createQuery(MyConstants.SQL_QUERY_COMPUTER_COUNT).getSingleResult();
			tx.commit();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
			tx.rollback();
		}
		return nb;
	}

	@Override
	public long getNumberElementLike(String sLike) {
		long nb = 0;
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			nb = (Long) session.createQuery(MyConstants.SQL_QUERY_COMPUTER_COUNT_LIKE)
					.setParameter("like", "%"+sLike+"%")
					.getSingleResult();
			tx.commit();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
			tx.rollback();
		}
		return nb;
	}
	
	@Override
	public long getNumberComputerRelatedToThisCompany(long idCompany) {
		long nb = 0;
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			nb = (Long) session.createQuery(MyConstants.SQL_QUERY_COMPUTER_COUNT_RELATED_COMPANY)
					.setParameter("idCompany", idCompany)
					.getSingleResult();
			tx.commit();
		}catch (HibernateException e) {
			logger.debug(e.getMessage());
			tx.rollback();
		}
		return nb;
	}
}
