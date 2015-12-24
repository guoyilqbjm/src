package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import error.ErrorInformation;

public class GetTaskInfo {
	public static String [] get(String username,String title){
		String result[] = new String[4];
		result[0] = username;
		result[1] = title;
		String sql = "select thismode,thatmode from task where username='"+username+"' && title='"+title+"'";
		try {
			ResultSet re = DataBaseInfo.statement.executeQuery(sql);
			if(re.next()){
				result[2]=re.getString(1);
				result[3]=re.getString(2);
			}
			else{
				ErrorInformation.set("找不到该任务！");
				System.out.println("找不到该任务！");
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
