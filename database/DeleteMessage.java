package database;

import java.sql.SQLException;

public class DeleteMessage {
	public static void delete(String time){
		String sql="delete from message where time='"+time+"'";
		try {
			DataBaseInfo.statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
}
