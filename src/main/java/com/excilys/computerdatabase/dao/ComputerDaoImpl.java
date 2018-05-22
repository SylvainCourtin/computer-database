package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyConstants;
import com.excilys.computerdatabase.utils.MyUtils;
import com.mysql.cj.api.jdbc.Statement;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	
	private Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MapperComputer mapperComputer;

	public ComputerDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public long add(Computer computer){
		
		long idRes = -1;
		
		if(computer.getName() != null && !computer.getName().equals(""))
		{
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final Long idCompany = computer.getManufacturerCompany() != null ? computer.getManufacturerCompany().getId() : null;
			
			jdbcTemplate.update((Connection connection) -> { return initPreparedStatementWithParameters(connection, MyConstants.SQL_QUERY_COMPUTER_INSERT, true,
					computer.getName(),
					MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()),
					MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()),
					idCompany);
				}
			, keyHolder);
			idRes = keyHolder.getKey().longValue();
		}
		else
			logger.debug("The name is empty !");
		return idRes;
	}

	@Override
	public List<Computer> getList(int limite, int offset) {
		List<Computer> computers = new ArrayList<>();
		
		try {
			computers = jdbcTemplate.query(MyConstants.SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY_LIMIT,mapperComputer, limite,offset);
		}catch (EmptyResultDataAccessException e) {
			logger.debug(e.getMessage());	
		}
		return computers;
	}
	
	@Override
	public List<Computer> getListLike(int limite, int offset, String sLike) {
		List<Computer> computers = new ArrayList<>();		
		try {
			computers = jdbcTemplate.query(MyConstants.SQL_QUERY_COMPUTER_SELECT_LIKE_LEFT_JOIN_COMPANY_LIMIT,mapperComputer,"%"+sLike+"%", limite,offset);
		}catch (EmptyResultDataAccessException e) {
			logger.debug(e.getMessage());	
		}
		return computers;
	}

	@Override
	public Optional<Computer> getComputer(long id) {
		Computer computer = null;
		try {
			computer = jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY,mapperComputer,id);
		}catch (EmptyResultDataAccessException e) {
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
		int res = jdbcTemplate.update(MyConstants.SQL_QUERY_COMPUTER_DELETE, id);
		if (res > 0)
				isDelete = true;
			
		return isDelete;
	}

	@Override
	public boolean update(Computer computer) {
		boolean isUpdate = false;
		if(computer.getName() != null && !computer.getName().equals(""))
		{
			Long companyId = computer.getManufacturerCompany().getId();
			
			int res = jdbcTemplate.update(MyConstants.SQL_QUERY_COMPUTER_UPDATE,
					computer.getName(),
					MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()),
					MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()),
					companyId,
					computer.getId() );			
			if (res > 0)
				isUpdate = true;	
		}
		
		return isUpdate;
		
	}

	@Override
	public long getNumberElement() {
		return jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPUTER_COUNT, Long.class);
	}

	@Override
	public long getNumberElementLike(String sLike) {
		return jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPUTER_COUNT_LIKE, Long.class, "%"+sLike+"%");
	}
	
	@Override
	public long getNumberComputerRelatedToThisCompany(long idCompany) {
		return jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPUTER_COUNT_RELATED_COMPANY, Long.class, idCompany);
	}
	
	/**
	 * Prepare et initialise la requete
	 * @param connection
	 * @param sql
	 * @param isReturnKey
	 * @param objects List of args of this request. Should be in the good order
	 * @return
	 * @throws SQLException
	 */
	private static PreparedStatement initPreparedStatementWithParameters(Connection connection, String sql, boolean isReturnKey, Object...objects) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement(sql, isReturnKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		int params = 1;
		for (Object object : objects) {
			preparedStatement.setObject(params, object);
			params++;
		}
		return preparedStatement;
}
}
