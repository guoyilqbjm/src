package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInfo {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost/iftttdatabase";
	
	private static final String USER = "root";
	private static final String PASS = "";
	
	public static  Connection connection = null;
	public static  Statement statement = null;
	
	public static void init() throws ClassNotFoundException, SQLException{
		Class.forName(JDBC_DRIVER);
		connection = DriverManager.getConnection(DB_URL,USER,PASS);
		statement = connection.createStatement();
	}
	
	public static void exit() throws SQLException{
		statement.close();
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		DataBaseInfo.init();
		DataBaseInfo.exit();
	}
}
