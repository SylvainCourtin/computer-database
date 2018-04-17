package com.excilys.computerdatabase.utils;

public class MyConstants {
	
	public static final String SQL_QUERY_COMPANY_SELECT = "SELECT * FROM company ";
	public static final String SQL_QUERY_COMPANY_INSERT = "INSERT INTO company (id,name) values ";

	public static final String SQL_QUERY_COMPUTER_SELECT = "SELECT * FROM computer ";
	public static final String SQL_QUERY_COMPUTER_LEFT_JOIN_COMPANY = " LEFT JOIN company ON company.id = computer.company_id ";
	public static final String SQL_QUERY_COMPUTER_INSERT = "INSERT INTO computer (id,name,introduced,discontinued,company_id) values ";
	public static final String SQL_QUERY_COMPUTER_DELETE = "DELETE FROM computer WHERE ";
	public static final String SQL_QUERY_COMPUTER_UPDATE = "UPDATE computer SET ";

}
