package database;

import java.sql.SQLException;

import error.ErrorInformation;

public class ChangeInformation {
	public static boolean change(String username,String newPassword){
		String sql = "update user set password='"+newPassword+"' where username='"+username+"'";
		try {
			DataBaseInfo.statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorInformation.set("�޸��û�������ʱ����");
			return false;
		}
		return true;
	}
}
