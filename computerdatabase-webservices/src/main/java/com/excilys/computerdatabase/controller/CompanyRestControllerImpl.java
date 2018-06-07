package com.excilys.computerdatabase.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.dtos.CompanyDTO;
import com.excilys.computerdatabase.exception.CompanyDoesNotExistException;
import com.excilys.computerdatabase.mappers.MapperCompany;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.service.ServiceCompany;

@RestController
@RequestMapping(value="/companies")
public class CompanyRestControllerImpl implements CompanyRestController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CompanyRestControllerImpl.class);
	
	private ServiceCompany serviceCompany;
	private MapperCompany mapperCompany;

	public CompanyRestControllerImpl(ServiceCompany serviceCompany, MapperCompany mapperCompany) {
		super();
		this.serviceCompany = serviceCompany;
		this.mapperCompany = mapperCompany;
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDTO> getCompany(@PathVariable long id) {
		ResponseEntity<CompanyDTO> response = null;
		try {
			Optional<Company> company = serviceCompany.getCompany(id);
			if(company.isPresent())
			{
				CompanyDTO companyDTO = mapperCompany.companyToDTO(company.get());
				response = new ResponseEntity<CompanyDTO>(companyDTO, HttpStatus.OK);
			}
			else
				throw new CompanyDoesNotExistException();
		}catch (ServiceException | CompanyDoesNotExistException e) {
			logger.debug(e.getMessage());
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@Override
	@GetMapping
	public ResponseEntity<List<CompanyDTO>> getCompanies() {
		try {
			return new ResponseEntity<List<CompanyDTO>>(serviceCompany
					.getCompanies(serviceCompany.getNumberRowComputer(),0)
					.stream()
					.map(company -> mapperCompany.companyToDTO(company) )
					.collect(Collectors.toList()), HttpStatus.OK);
		}catch (ServiceException e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
