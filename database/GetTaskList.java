package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Task.Task;
import Task.TaskList;

public class GetTaskList {
	public static ArrayList<String> get(String username){
		ArrayList<String> result = new ArrayList<String>();
		String sql = "select username,title,thisMode,thatMode from task where username='"+username+"'";
		try {
			ResultSet re=DataBaseInfo.statement.executeQuery(sql);
			while(re.next()){
				String title=re.getString(2),thisMode=re.getString(3),thatMode=re.getString(4);
				String status = "stopped";
				ArrayList<Task> runTask = TaskList.getTaskList();
				for(int i=0;i<runTask.size();++i){
					if(runTask.get(i).getTitle().equals(title) && runTask.get(i).getUserName().equals(username)){
						status = "running";
					}
				}
				result.add(title+";"+thisMode+";"+thatMode+";"+status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
