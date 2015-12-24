package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetMessageList {
	public static ArrayList<String> get(){
		ArrayList<String>result = new ArrayList<String>();
		String sql="select time,receiver,content from message";
		ResultSet re=null;
		try {
			re=DataBaseInfo.statement.executeQuery(sql);
			while(re.next()){
				String type="";
				String username="";
				if(re.getString(2).equals("")){
					type="public";
					username="all";
				}
				else{
					type="private";
					username=re.getString(2);
				}
				result.add(re.getString(1)+","+type+","+username+","+re.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
