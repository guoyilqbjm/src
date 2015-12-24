package database;
import java.sql.SQLException;

import database.DataBaseInfo;
public class LoginIn {
	public static boolean loginIn(String username,String password) throws SQLException, ClassNotFoundException{
		if(DataBaseInfo.connection == null){
			DataBaseInfo.init();
		}
		String sql = "select username,password from user where username='" + username + "' && password='" + password + "'";
		return (DataBaseInfo.statement.executeQuery(sql).next());
	}
	
	public static void main(String args[]) throws SQLException, ClassNotFoundException{
		String username="guoyi",password="123456";
		System.out.println(LoginIn.loginIn(username, password));
	}
}
