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
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.dtos.ComputerDTO;
import com.excilys.computerdatabase.mappers.MapperComputer;
import com.excilys.computerdatabase.service.ServiceComputer;

@RestController
@RequestMapping("/computer")
public class ComputerRestControllerImpl implements ComputerRestController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ComputerRestControllerImpl.class);
	
	private ServiceComputer serviceComputer;
	private MapperComputer mapperComputer;

	public ComputerRestControllerImpl(ServiceComputer serviceComputer, MapperComputer mapperComputer) {
		this.serviceComputer = serviceComputer;
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
	@GetMapping
	public ResponseEntity<List<ComputerDTO> > getComputers() {
		try {
			return new ResponseEntity<List<ComputerDTO> >(serviceComputer
					.getComputers((int)serviceComputer.getNumberRowComputer(), 0)
					.stream()
					.map(computer -> MapperComputer.computerToDTO(computer))
					.collect(Collectors.toList()), HttpStatus.OK);
		}catch (ServiceException e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Override
	@GetMapping("/search/{search}")
	public ResponseEntity<List<ComputerDTO> > getComputersSearch(@PathVariable String search) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@Override
	@PostMapping
	public ResponseEntity<String> createComputer(@RequestBody ComputerDTO computerDTO) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@Override
	@PutMapping("/{id}")
	public ResponseEntity<String> updateComputer(@PathVariable long id, @RequestBody ComputerDTO computerDTO) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteComputer(@PathVariable long id) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@Override
	@GetMapping("/count")
	public ResponseEntity<Integer> getCountComputers() {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@Override
	@GetMapping("/search/{search}/count")
	public ResponseEntity<Integer> getCountComputersSearch(@PathVariable String search) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	

}
