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

import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.utils.MyConstants;
import com.excilys.computerdatabase.utils.MyUtils;

@Repository
public class ComputerDaoImpl implements ComputerDao {
	
	private Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	@Autowired
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
		{/*
			Long companyId = companyExist(computer.getManufacturerCompany());
			
			try(Connection connection = daoFactory.getConnection();
				PreparedStatement preparedStatement = initPreparedStatementWithParameters(connection, MyConstants.SQL_QUERY_COMPUTER_INSERT, true,
					computer.getName(),
					MyUtils.formatDateUtilToSQLDate(computer.getDateIntroduced()),
					MyUtils.formatDateUtilToSQLDate(computer.getDateDiscontinued()),
					companyId)
				)
			{
				preparedStatement.executeUpdate();
				try(ResultSet resultSet = preparedStatement.getGeneratedKeys();)
				{
					while(resultSet.next())
					{
						idRes = resultSet.getLong(1);
					}
				}

			}catch (SQLException e) {
				e.printStackTrace();
			}*/
		}

		
		return idRes;
	}

	@Override
	public List<Computer> getList(int limite, int offset) {
		List<Computer> computers = new ArrayList<>();
		
		try {
			computers = jdbcTemplate.query(MyConstants.SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY_LIMIT,mapperComputer, limite,offset);
		}catch (EmptyResultDataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();	
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
			e.printStackTrace();	
		}
		return computers;
	}

	@Override
	public Optional<Computer> getComputer(long id) {
		Computer computer = null;
		try {
			computer = jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPUTER_SELECT_LIKE_LEFT_JOIN_COMPANY_LIMIT,mapperComputer,id);
		}catch (EmptyResultDataAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();	
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
		return jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPUTER_COUNT_LIKE, Long.class, sLike);
	}
	
	@Override
	public long getNumberComputerRelatedToThisCompany(long idCompany) {
		return jdbcTemplate.queryForObject(MyConstants.SQL_QUERY_COMPANY_COUNT, Long.class, idCompany);
	}
}
