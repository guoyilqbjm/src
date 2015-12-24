package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import error.ErrorInformation;

public class ChangeMessage {
	public static boolean change(String time,String content){
		String sql;
		String receiver="";
		sql="select receiver from message where time='"+time+"'";
		try {
			ResultSet re= DataBaseInfo.statement.executeQuery(sql);
			if(re.next()){
				receiver=re.getString(1);
			}
			else
			{
				return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pretime=date_format.format(ca.getTime());
		try {
			sql="update message set content='"+content+"' where time='"+time+"'";
			DataBaseInfo.statement.execute(sql);
			sql="update message set time='"+pretime+"' where time='"+ time + "'";
			DataBaseInfo.statement.execute(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorInformation.set("修改消息数据库操作失败！");
			return false;
		}
	}
}
