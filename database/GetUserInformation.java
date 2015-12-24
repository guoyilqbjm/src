package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserInformation {
	public static String get(String username){
		String result="";
		String sql = "select username,balance,score,level from user where username='"+username+"'";
		ResultSet re = null;
		try {
			if(DataBaseInfo.statement==null){
				try {
					DataBaseInfo.init();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			re = DataBaseInfo.statement.executeQuery(sql);
			while(re.next()){
				result=re.getString(1)+","+re.getString(2)+","+re.getString(3)+","+re.getString(4);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
