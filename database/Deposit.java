package database;

import java.sql.SQLException;

import error.ErrorInformation;

public class Deposit {
	public static boolean deposit(String username,int count){
		String sql = "update user set balance = balance + " + count + "where username = '" + username +"'";
		try {
			DataBaseInfo.statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorInformation.set("��ֵʱ��������");
			return false;
		}
		return true;
	}
}
