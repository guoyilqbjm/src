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
			ErrorInformation.set("删除任务时sql语句有误！"+" "+sql);
			return false;
		}
		return true;
	}
}
