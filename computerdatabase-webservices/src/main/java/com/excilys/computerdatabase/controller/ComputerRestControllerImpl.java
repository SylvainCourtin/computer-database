package com.excilys.computerdatabase.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.exception.ValidationException;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.service.ServiceComputer;
import com.excilys.computerdatabase.utils.Utils;

@RestController
@RequestMapping("/computer")
public class ComputerRestControllerImpl implements ComputerRestController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ComputerRestControllerImpl.class);
	
	private ServiceComputer serviceComputer;
	private MapperComputer mapperComputer;

	public ComputerRestControllerImpl(ServiceComputer serviceComputer, MapperComputer mapperComputer) {
		this.serviceComputer = serviceComputer;
		this.mapperComputer = mapperComputer;
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTO> getComputerById(@PathVariable long id) {
		ResponseEntity<ComputerDTO> response = null;
		try {
			Optional<ComputerDTO> computerDTO = serviceComputer.getComputerDTO(id);
			if(computerDTO.isPresent())
				response = new ResponseEntity<ComputerDTO>(computerDTO.get(), HttpStatus.OK);
			else
			{
				logger.debug("Computer not found");
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (ServiceException e) {
			logger.debug(e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	@Override
	@GetMapping(params= {"page", "search", "limit"})
	public ResponseEntity<List<ComputerDTO> > getComputers(
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue=Utils.NUMBER_LIST_PER_PAGE+"") int limit,
			@RequestParam(defaultValue="") String search) {
		try {
			return new ResponseEntity<List<ComputerDTO> >(serviceComputer
					.getComputers(limit, pageManager(search,page,limit), search)
					.stream()
					.map(computer -> MapperComputer.computerToDTO(computer))
					.collect(Collectors.toList()), HttpStatus.OK);
		}catch (ServiceException e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@PostMapping
	public ResponseEntity<String> createComputer(@RequestBody ComputerDTO computerDTO) {
		ResponseEntity<String> response;
		try {
			long id = serviceComputer.addComputer(mapperComputer.fromParameters(computerDTO));
			if( id > 0 )
				response = new ResponseEntity<String>(id+"",HttpStatus.OK);
			else
				throw new ServiceException("Error while creating the computer");
		}catch (ValidationException | ServiceException e) {
			logger.debug(e.getMessage());
			if(e instanceof ValidationException)
				response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			else
				response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	@Override
	@PutMapping("/{id}")
	public ResponseEntity<String> updateComputer(@PathVariable long id, @RequestBody ComputerDTO computerDTO) {
		ResponseEntity<String> response;
		try {
			Optional<ComputerDTO> oldComputerDTO = serviceComputer.getComputerDTO(id);
			if(oldComputerDTO.isPresent())
			{
				if( serviceComputer.updateComputer(mapperComputer.fromParameters(oldComputerDTO.get()), mapperComputer.fromParameters(computerDTO)) )
					response = new ResponseEntity<>(HttpStatus.OK);
				else
					throw new ServiceException("Error while updating the computer");
			}
			else
				throw new ValidationException("ID computer not found");
			
		}catch (ValidationException | ServiceException e) {
			logger.debug(e.getMessage());
			if(e instanceof ValidationException)
				response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			else
				response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteComputer(@PathVariable long id) {
		ResponseEntity<String> response;
		try {
			Optional<ComputerDTO> oldComputerDTO = serviceComputer.getComputerDTO(id);
			if(oldComputerDTO.isPresent())
			{
				if(serviceComputer.deleteComputer(id))
					response = new ResponseEntity<>(HttpStatus.OK);
				else
					throw new ValidationException("Error while deleting the computer");
			}
			else
				response = new ResponseEntity<String>("Computer not found", HttpStatus.BAD_REQUEST);
		}catch (ServiceException | ValidationException e) {
			logger.debug(e.getMessage());
			if(e instanceof ValidationException)
				response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			else
				response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	@Override
	@GetMapping({"/count"})
	public ResponseEntity<Long> getCountComputers(@RequestParam(defaultValue="") String search) {
		ResponseEntity<Long> response;
		try {
			response = new ResponseEntity<Long>(serviceComputer.getNumberRowComputerLike(search), HttpStatus.OK);
		}catch (ServiceException e) {
			logger.debug(e.getMessage());
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	private int pageManager(String search, int nextPage, int limitPerPage)
	{
		long numberOfComputer = serviceComputer.getNumberRowComputerLike(search);
		int res = 0;
		if(numberOfComputer%limitPerPage > 0)
			res=1;
		int numberOfPages=(int) numberOfComputer/limitPerPage + res;
		int page=1;
		if(nextPage > 0)
		{
			page = nextPage;
			if(limitPerPage*(page-1) > numberOfComputer)
				page =  numberOfPages;
				
		}
		return page;
	}
	

}
