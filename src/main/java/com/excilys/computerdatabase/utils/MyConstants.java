package com.excilys.computerdatabase.utils;

public class MyConstants {
	
	public static final String SQL_QUERY_COMPANY_SELECT_LIMIT = "SELECT id,name FROM company "
			+ "ORDER BY name "
			+ "LIMIT ? OFFSET ?;";
	public static final String SQL_QUERY_COMPANY_SELECT = "SELECT id,name FROM company ";
	public static final String SQL_QUERY_COMPANY_INSERT = "INSERT INTO company (name) VALUES (?);";
	public static final String SQL_QUERY_COMPANY_COUNT = "SELECT COUNT(id) FROM company;";
	public static final String SQL_QUERY_COMPANY_DELETE = "DELETE FROM company WHERE id=? ;";
	
	//------------------------------------------------------------------------------------------

	public static final String SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY_LIMIT = "SELECT * FROM computer LEFT JOIN company "
			+ "ON company.id = computer.company_id "
			+ "ORDER BY computer.name "
			+ "LIMIT ? OFFSET ? ;";
	public static final String SQL_QUERY_COMPUTER_SELECT_LIKE_LEFT_JOIN_COMPANY_LIMIT = "SELECT * FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "WHERE computer.name LIKE ? "
			+ "ORDER BY computer.name "
			+ "LIMIT ? OFFSET ? ;";
	public static final String SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY = "SELECT * FROM computer LEFT JOIN company "
			+ "ON company.id = computer.company_id WHERE computer.id=?;";
	public static final String SQL_QUERY_COMPUTER_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	public static final String SQL_QUERY_COMPUTER_DELETE = "DELETE FROM computer WHERE id=?;";
	public static final String SQL_QUERY_COMPUTER_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	public static final String SQL_QUERY_COMPUTER_COUNT = "SELECT COUNT(id) FROM computer;";
	public static final String SQL_QUERY_COMPUTER_COUNT_LIKE = "SELECT COUNT(id) FROM computer WHERE name LIKE ?;";
	
	public static final String SQL_QUERY_COMPUTER_COUNT_RELATED_COMPANY = "SELECT COUNT(computer.id) FROM computer "
			+ "INNER JOIN company ON company.id = computer.company_id "
			+ "WHERE computer.company_id=?; ";
	public static final String SQL_QUERY_COMPUTER_DELETE_RELATED_COMPANY = "DELETE FROM computer WHERE computer.company_id=?;";
	
	public static final int NUMBER_LIST_PER_PAGE = 10;
	
	public static final String COUNT = "COUNT(id)";
	public static final String COUNT_COMPUTER = "COUNT(computer.id)";

}
