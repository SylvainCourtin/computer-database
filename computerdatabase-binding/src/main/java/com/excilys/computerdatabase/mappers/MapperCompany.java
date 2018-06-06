package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.models.Company;

@Component
public class MapperCompany implements RowMapper<Company> {
	
	@Autowired
	private CompanyDao companyDao;

	public Company fromParameters(long id, String name)
	{
		Company company = null;
		if(id > 0)
			company = new Company(id,name);
		return company;
	}
	
	public CompanyDTO companyToDTO(Company company)
	{
		return new CompanyDTO(company);
	}
	
	public Optional<Company> fromIdCompany(long id)
	{
		if(id > 0)
		{
			return companyDao.getCompany(id);
		}
		return Optional.empty();
	}
	
	public Optional<CompanyDTO> fromIdCompanyDTO(long id)
	{
		if(id > 0)
		{
			Optional<Company> company = companyDao.getCompany(id);
			if(company.isPresent())
				return Optional.ofNullable(companyToDTO(company.get()));
		}
		return Optional.empty();
	}

	@Override
	public Company mapRow(ResultSet resultSet, int rownum) throws SQLException {
		return fromParameters(resultSet.getLong("id"), resultSet.getString("name"));
	}
	
}