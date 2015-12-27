package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import error.ErrorInformation;

public class ChangeTaskInformation {
	public static void change(String username,String title,String thismode,String thatmode){
		String sql;
		sql="delete from task where username='"+username+"' && title='"+title+"'";
		try {
			DataBaseInfo.statement.execute(sql);
			sql="insert into task values ('"+username+"','"+title+"','"+thismode+"','"+thatmode+"')";
			DataBaseInfo.statement.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
