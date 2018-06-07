package com.excilys.computerdatabase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.excilys.computerdatabase.dtos.ComputerDTO;

public interface ComputerRestController {
	
	public ResponseEntity<ComputerDTO> getComputerById(long id);
    public ResponseEntity<List<ComputerDTO> > getComputers();
    public ResponseEntity<List<ComputerDTO> > getComputersSearch(String search);

    public ResponseEntity<String> createComputer(ComputerDTO computerDTO);
    public ResponseEntity<String> updateComputer(long id, ComputerDTO computerDTO);
    public ResponseEntity<String> deleteComputer(long id);
    public ResponseEntity<Integer> getCountComputers();
    public ResponseEntity<Integer> getCountComputersSearch(String search);
}
