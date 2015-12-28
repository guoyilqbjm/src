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
				ErrorInformation.set("修改金额错误！");
				ErrorInformation.setBasePage("managetasks.jsp");
				return;
			}
			int balance = re.getInt(1)+deposit;
			sql="update user set balance="+balance+" where username='"+username+"'";
			DataBaseInfo.statement.execute(sql);
			sql="insert into transaction values ('"+username+"','"+time+"',"+deposit+")";
			DataBaseInfo.statement.execute(sql);
			if(deposit<0){
				sql="select score,level from user where username='"+username+"'";
				re=DataBaseInfo.statement.executeQuery(sql);
				if(!re.next()){
					ErrorInformation.set("修改积分错误！");
					ErrorInformation.setBasePage("managetasks.jsp");
					return;
				}
				int score=re.getInt(1),level=re.getInt(2);
				score=score-deposit;
				if(score<30)
					level=1;
				else if(score<70)
					level=2;
				else if(score<120)
					level=3;
				else if(score<180)
					level=4;
				else
					level=5;
				sql="update user set score="+score+" && level="+level+" where username='"+username+"'";
				DataBaseInfo.statement.execute(sql);
			}
			
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
