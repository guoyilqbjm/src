package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetUsers {
	public static ArrayList<String> get(){
		ArrayList<String> result = new ArrayList<String>();
		String sql = "select username from user";
		ResultSet re;
		try {
			if(DataBaseInfo.statement==null){
				DataBaseInfo.init();
			}
			re = DataBaseInfo.statement.executeQuery(sql);
			if(re==null){
				System.out.println("sss");
			}
			while(re.next()){
				result.add(re.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
