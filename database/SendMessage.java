package database;

import java.sql.SQLException;

public class SendMessage {
	public static void send(String time,String receiver,String content){
		String sql="insert into message values ('"+time+"','"+receiver+"','"+content+"')";
		
		try {
			DataBaseInfo.statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
