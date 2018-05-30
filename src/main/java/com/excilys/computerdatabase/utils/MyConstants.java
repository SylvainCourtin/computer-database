package com.excilys.computerdatabase.utils;

public class MyConstants {
	
	public static final String SQL_QUERY_COMPANY_SELECT_ORDERBY = "FROM Company "
			+ "ORDER BY name ";
	public static final String SQL_QUERY_COMPANY_SELECT = "FROM Company ";
	public static final String SQL_QUERY_COMPANY_SELECT_WHERE_ID = "FROM Company where id= :idCompany";
	public static final String SQL_QUERY_COMPANY_INSERT = "INSERT INTO Company (name) VALUES (:name)";
	public static final String SQL_QUERY_COMPANY_COUNT = "SELECT COUNT(id) FROM Company";
	public static final String SQL_QUERY_COMPANY_DELETE = "DELETE FROM Company WHERE id= :idCompany ";
	
	//------------------------------------------------------------------------------------------

	public static final String SQL_QUERY_COMPUTER_SELECT_ORDERBY = "FROM Computer "
			+ "ORDER BY name ";
	public static final String SQL_QUERY_COMPUTER_SELECT_LIKE_ORDERBY = "FROM Computer "
			+ "WHERE Computer.name LIKE :like "
			+ "ORDER BY Computer.name ";
	public static final String SQL_QUERY_COMPUTER_SELECT_WHERE = "FROM Computer WHERE Computer.id= :idComputer";
	public static final String SQL_QUERY_COMPUTER_INSERT = "INSERT INTO Computer (name,introduced,discontinued,company_id) VALUES (:name,:introduced,:discontinued,:idCompany)";
	public static final String SQL_QUERY_COMPUTER_DELETE = "DELETE FROM Computer WHERE id= :idComputer";
	public static final String SQL_QUERY_COMPUTER_UPDATE = "UPDATE Computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:idCompany WHERE id=:idCompany";
	public static final String SQL_QUERY_COMPUTER_COUNT = "SELECT COUNT(c.id) FROM Computer c";
	public static final String SQL_QUERY_COMPUTER_COUNT_LIKE = "SELECT COUNT(c.id) FROM Computer c WHERE name LIKE :like";
	
	public static final String SQL_QUERY_COMPUTER_COUNT_RELATED_COMPANY = "SELECT COUNT(c.id) FROM Computer c "
			+ "INNER JOIN Company ON c.id = c.company_id "
			+ "WHERE c.company_id=:idCompany; ";
	public static final String SQL_QUERY_COMPUTER_DELETE_RELATED_COMPANY = "DELETE FROM Computer WHERE Computer.company_id=:idCompany";
	
	//------------------------------------------------------------------------------------------
	
	public static final int NUMBER_LIST_PER_PAGE = 10;
	
	public static final String COUNT = "COUNT(id)";
	public static final String COUNT_COMPUTER = "COUNT(Computer.id)";

}
