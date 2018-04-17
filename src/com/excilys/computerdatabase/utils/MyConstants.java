package com.excilys.computerdatabase.utils;

public class MyConstants {
	
	public static final String SQL_QUERY_COMPANY_SELECT = "SELECT * FROM company ";
	public static final String SQL_QUERY_COMPANY_INSERT = "INSERT INTO company (name) VALUES (?);";

	public static final String SQL_QUERY_COMPUTER_SELECT = "SELECT * FROM computer ";
	public static final String SQL_QUERY_COMPUTER_LEFT_JOIN_COMPANY = " LEFT JOIN company ON company.id = computer.company_id ";
	public static final String SQL_QUERY_COMPUTER_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	public static final String SQL_QUERY_COMPUTER_DELETE = "DELETE FROM computer WHERE id=";
	public static final String SQL_QUERY_COMPUTER_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=?";

}
