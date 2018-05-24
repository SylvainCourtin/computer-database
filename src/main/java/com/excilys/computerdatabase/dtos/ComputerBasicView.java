package com.excilys.computerdatabase.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.excilys.computerdatabase.models.Computer;

public class ComputerBasicView {
	
	private long id;
	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate introduced;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate discontinued;
	
	public ComputerBasicView() {
	}

	public ComputerBasicView(Computer computer) {
		id = computer.getId();
		name = computer.getName();
		introduced = null;
		discontinued = null;
		if(computer.getDateIntroduced() != null)
			introduced = computer.getDateIntroduced();
		if(computer.getDateDiscontinued() != null)
			discontinued = computer.getDateDiscontinued();
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

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	
	
}
