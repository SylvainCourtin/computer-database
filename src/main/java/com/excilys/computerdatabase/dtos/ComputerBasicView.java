package com.excilys.computerdatabase.dtos;

import java.util.Date;

import com.excilys.computerdatabase.models.Computer;

public class ComputerBasicView {
	
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;

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

	public ComputerBasicView(long id, String name, Date introduced, Date discontinued) {
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

	public Date getIntroduced() {
		return introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}
}
