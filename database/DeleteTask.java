package database;

import java.sql.SQLException;

import error.ErrorInformation;

public class DeleteTask {
	public static boolean deleteTask(String username,String title){
		String sql = "delete from task where username = '" + username + "' && title = '" + title + "'";
		try {
			DataBaseInfo.statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorInformation.set("ɾ������ʱsql�������"+" "+sql);
			return false;
		}
		return true;
	}
}
