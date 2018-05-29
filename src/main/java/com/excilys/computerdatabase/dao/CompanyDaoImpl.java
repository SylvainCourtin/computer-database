package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.utils.MyConstants;

@Repository
public class CompanyDaoImpl extends HibernateDAO implements CompanyDao {
	
	private Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
	
	@Autowired
	private MapperCompany mapperCompany;
	
	public CompanyDaoImpl() {
		super();
	}

	@Override
	public List<Company> getList(int limite, int offset) {
		List<Company> companies = new ArrayList<>();
//		try {
//			companies = jdbcTemplate.query(MyConstants.SQL_QUERY_COMPANY_SELECT_LIMIT,mapperCompany, limite,offset);
//		}catch (EmptyResultDataAccessException e) {
//			logger.debug(e.getMessage());	
//		}
		return companies;
	}
	
	@Override
	public Optional<Company> getCompany(long id)
	{
		Company company = null;
//		try {
//			company = jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPANY_SELECT+"WHERE id="+id+" ORDER BY name DESC ;", mapperCompany);
//		}catch (EmptyResultDataAccessException e) {
//			logger.debug(e.getMessage());
//			company = null;
//		}
		
		return Optional.ofNullable(company);
	}

	@Override
	public long getNumberElement() {
//		return jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPANY_COUNT, Long.class);
		return 0;
	}
	
	@Override
	@Transactional(rollbackFor= {Throwable.class}, noRollbackFor= {CompanyDoesNotExistException.class})
	public boolean deleteCompany(long id) throws CompanyDoesNotExistException {
		boolean isDelete = false;
//		if(getCompany(id).isPresent())
//		{
//			jdbcTemplate.update(MyConstants.SQL_QUERY_COMPUTER_DELETE_RELATED_COMPANY,id);
//			jdbcTemplate.update(MyConstants.SQL_QUERY_COMPANY_DELETE,id);
//			isDelete = true;
//		}
//		else
//			throw new CompanyDoesNotExistException();
		return isDelete;
	}
}
