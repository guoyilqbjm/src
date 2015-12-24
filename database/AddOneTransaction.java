package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import error.ErrorInformation;

public class AddOneTransaction {
	public static void add(String username,int deposit){
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=date_format.format(ca.getTime());
		try {
			String sql="select balance from user where username='"+username+"'";
			ResultSet re=DataBaseInfo.statement.executeQuery(sql);
			if(!re.next()){
				ErrorInformation.set("ÐÞ¸Ä½ð¶î´íÎó£¡");
				return;
			}
			int balance = re.getInt(1)+deposit;
			sql="update user set balance="+balance+" where username='"+username+"'";
			DataBaseInfo.statement.execute(sql);
			sql="insert into transaction values ('"+username+"','"+time+"',"+deposit+")";
			DataBaseInfo.statement.execute(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String arg[]) throws ClassNotFoundException, SQLException{
		DataBaseInfo.init();
		AddOneTransaction.add("guoyi", -100);
		DataBaseInfo.exit();
	}
}
