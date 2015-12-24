package database;

import java.sql.SQLException;

import error.ErrorInformation;

public class UserExisted {
	public static boolean is(String username){
		if(username.equals("admin"))
			return true;
		String sql = "select username,password from user where username = '" + username + "'";
		try {
			if(DataBaseInfo.statement.executeQuery(sql).next()){
				ErrorInformation.set("用户"+username+"已经存在，无法创建!");
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
