package com.excilys.computerdatabase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.excilys.computerdatabase.dtos.CompanyDTO;

public interface CompanyRestController {
	/**
	 * return a companyDTO, if not found return the http code not_found
	 * @param id
	 * @return
	 */
	ResponseEntity<CompanyDTO> getCompany(long id);
	/**
	 * list of companies or http code error
	 * @return
	 */
	ResponseEntity<List<CompanyDTO> > getCompanies();
}
