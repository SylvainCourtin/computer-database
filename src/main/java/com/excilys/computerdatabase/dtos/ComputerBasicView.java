package com.excilys.computerdatabase.dtos;

import java.time.LocalDate;
import java.time.ZoneId;

import com.excilys.computerdatabase.models.Computer;

public class ComputerBasicView {
	
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;

	public ComputerBasicView(Computer computer) {
		id = computer.getId();
		name = computer.getName();
		introduced = computer.getDateIntroduced().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		discontinued = computer.getDateDiscontinued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public ComputerBasicView(long id, String name, LocalDate introduced, LocalDate discontinued) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}
}
