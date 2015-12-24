package database;

import java.sql.SQLException;

public class Register {
	public static void register(String username,String password) throws SQLException{
		String sql = "insert into user values ('" + username + "','" + password + "',100,1,0)";
		DataBaseInfo.statement.execute(sql);
	}
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		DataBaseInfo.init();
		Register.register("guoyi", "123456");
		DataBaseInfo.exit();
	}
}
