package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import error.*;

public class AddTask {
	public static boolean newTask(String username, String title,String thisMode,String thatMode){
		String sql = "select title from task where username = '" + username + "' && title = '" + title + "'";
		ResultSet re = null;
		try {
			if(DataBaseInfo.connection==null){
				System.out.println("数据库没有链接");
			}
			re = DataBaseInfo.statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {
			if(re.next()){
				ErrorInformation.set("任务名重复！");
				return false;
			}
			else{
				sql = "insert into task values ('" + username + "','" + title + "','" + thisMode + "','" + thatMode + "')";
				DataBaseInfo.statement.execute(sql);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	public static void main(String argsp[]) throws SQLException, ClassNotFoundException{
		DataBaseInfo.init();
		AddTask.newTask("guoyi","33", " ", " ");
	}
}
