package com.excilys.computerdatabase.models;

import org.springframework.stereotype.Component;

@Component
public class Company {

	private long id;
	private String name;
	
	public Company() {
	}
	
	public Company(String name) {
		this();
		this.name = name;
	}

	public Company(long idCompany, String name) {
		this(name);
		this.id = idCompany;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long idCompany) {
		this.id = idCompany;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
	

}
