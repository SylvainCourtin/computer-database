package com.excilys.computerdatabase.utils;

public class MyConstants {
	
	public static final String SQL_QUERY_COMPANY_SELECT_LIMIT = "SELECT * FROM company LIMIT ? OFFSET ? ;";
	public static final String SQL_QUERY_COMPANY_SELECT = "SELECT * FROM company ";
	public static final String SQL_QUERY_COMPANY_INSERT = "INSERT INTO company (name) VALUES (?);";
	public static final String SQL_QUERY_COMPANY_COUNT = "SELECT COUNT(*) FROM company;";

	public static final String SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY_LIMIT = "SELECT * FROM computer LEFT JOIN company ON company.id = computer.company_id LIMIT ? OFFSET ? ;";
	public static final String SQL_QUERY_COMPUTER_SELECT_LIKE_LEFT_JOIN_COMPANY_LIMIT = "SELECT * FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "WHERE computer.name LIKE ? LIMIT ? OFFSET ? ;";
	public static final String SQL_QUERY_COMPUTER_SELECT_LEFT_JOIN_COMPANY = "SELECT * FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id=?;";
	public static final String SQL_QUERY_COMPUTER_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	public static final String SQL_QUERY_COMPUTER_DELETE = "DELETE FROM computer WHERE id=";
	public static final String SQL_QUERY_COMPUTER_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	public static final String SQL_QUERY_COMPUTER_COUNT = "SELECT COUNT(*) FROM computer;";
	public static final String SQL_QUERY_COMPUTER_COUNT_LIKE = "SELECT COUNT(*) FROM computer WHERE name LIKE ?;";
	
	public static final int NUMBER_LIST_PER_PAGE = 10;

}
