package com.excilys.computerdatabase.dao;

import java.sql.Connection;

public abstract class DAO<T> {
	
	protected Connection connection;
	
	public DAO(Connection connection)
	{
		this.connection = connection;
	}
	
	public abstract boolean create(T obj);
	public abstract boolean delete(T obj);
	public abstract boolean update(T obj);
	public abstract T find(int id);

}
