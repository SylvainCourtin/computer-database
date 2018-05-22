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

import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.utils.MyConstants;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	
	private Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MapperCompany mapperCompany;

	public CompanyDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Company> getList(int limite, int offset) {
		List<Company> companies = new ArrayList<>();
		try {
			companies = jdbcTemplate.query(MyConstants.SQL_QUERY_COMPANY_SELECT_LIMIT,mapperCompany, limite,offset);
		}catch (EmptyResultDataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			
		}
		return companies;
	}
	
	@Override
	public Optional<Company> getCompany(long id)
	{
		Company company = null;
		try {
			company = jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPANY_SELECT+"WHERE id="+id+" ORDER BY name DESC ;", mapperCompany);
		}catch (EmptyResultDataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			
		}
		
		return Optional.ofNullable(company);
	}

	@Override
	public long getNumberElement() {
		return jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPANY_COUNT, Long.class);
	}
	
	@Override
	public boolean deleteCompany(long id) throws CompanyDoesNotExistException {
		boolean isDelete = false;
		/*if(getCompany(id).isPresent())
		{
			try (Connection connection = daoFactory.getConnection();)
			{
				connection.setAutoCommit(false);
				try(PreparedStatement preparedStatementDeleteCompany = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPANY_DELETE,false, id);
					PreparedStatement preparedStatementDeleteAllComputer = initPreparedStatementWithParameters(connection,MyConstants.SQL_QUERY_COMPUTER_DELETE_RELATED_COMPANY,false,id))
				{
					if(preparedStatementDeleteAllComputer.executeUpdate() > 0)
						if(preparedStatementDeleteCompany.executeUpdate() > 0)
							isDelete = true;
					connection.commit();
				}catch(SQLException e) {
					logger.warn("In dao.CompanyDaoImpl method deleteCompany : ", e.getMessage());
					isDelete = false;
					connection.rollback();
				}
				connection.setAutoCommit(true);
			}catch (SQLException e) {
				logger.warn("In dao.CompanyDaoImpl method deleteCompany : ", e.getMessage());
				isDelete = false;
			}
		}
		else
			throw new CompanyDoesNotExistException();*/
		return isDelete;
	}
}
