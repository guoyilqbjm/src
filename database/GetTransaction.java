package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetTransaction {
	public static ArrayList<String> get(String username){
		ArrayList<String> result = new ArrayList<String>();
		String sql = "select time,amount from transaction where username='"+username+"'";
		ResultSet re;
		try {
			re = DataBaseInfo.statement.executeQuery(sql);
			while(re.next()){
				String temp=re.getString(1)+","+re.getString(2);
				result.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
