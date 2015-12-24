package Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import error.ErrorInformation;

public class TaskList {
	public static ArrayList<Task> tasklist = new ArrayList<Task>();
	
	public static ArrayList<Task> getTaskList(){
		return tasklist;
	}
	
	static class StartTask extends TimerTask{
		private Task task;
		public StartTask(Task newTask){
			task = newTask;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			task.run();
			if(task.getState()==1){
				for(int i=0;i<tasklist.size();++i){
					if(tasklist.get(i).getTitle().equals(task.getTitle())&& tasklist.get(i).getUserName().equals(task.getUserName())){
						task=tasklist.get(i);
						task.stop();
						tasklist.remove(i);
						ErrorInformation.set("�����������������޷����У�");
					}
				}
			}
		}
		
	}

	private static Task newTask(String username,String title,String thisMod,String thatMod){
		Task newTask = new Task();
		newTask.setTitle(title);
		newTask.setUserName(username);
		String thisMods[] = thisMod.split(",");
		String thatMods[] = thatMod.split(",");
		if(thisMods[0].equals("1")){
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			String tempdata[] = thisMods[1].split(" ");
			String data[] = tempdata[0].split("-");
			String time[] = tempdata[1].split(":");
			calendar.set(Integer.parseInt(data[0]), Integer.parseInt(data[1])-1,Integer.parseInt(data[2]),//����������
					Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));//����ʱ����
			newTask.addThisTask1(calendar.getTime());
		}
		else if(thisMods[0].equals("2")){
			newTask.addThisTask2(thisMods[1], thisMods[2]);
		}
		else if(thisMods[0].equals("3")){
			
		}
		
		if(thatMods[0].equals("1")){
			String minfo[] = new String[6];
			minfo[0] = thatMods[1];
			minfo[1] = thatMods[2];
			minfo[2] = thatMods[3];
			minfo[3] = thatMods[1];
			minfo[4] = thatMods[4];
			minfo[5] = thatMods[5];
			newTask.addThatTask1(minfo);
		}
		else if(thatMods[0].equals("2")){
			String infor[] = new String[3];
			infor[0]=thatMods[1];infor[1]=thatMods[2];infor[2]=thatMods[3];
			newTask.addThatTask2(infor);
		}
		return newTask;
	}
	
	public static boolean startTask(String info[]){
		
		assert(info.length == 4);
		String username = info[0],title=info[1],thisMode=info[2],thatMode=info[3];
		
		Task task = null;
		for(int i=0;i<tasklist.size();++i){
			if(tasklist.get(i).getTitle().equals(title) && tasklist.get(i).getUserName().equals(username)){
				ErrorInformation.set("�����Ѿ���ʼ���޷��ٴο�ʼ��");
				return false;
			}
		}
		task  = newTask(username,title,thisMode,thatMode);
		
		tasklist.add(task);
		
		Timer timer = new Timer();
		task.setTimer(timer);
		task.setState(0);
		timer.schedule(new StartTask(task), 0,1000);
		return true;
	}
	
	
	public static boolean stopTask(String username,String title){
		Task task = null;
		for(int i=0;i<tasklist.size();++i){
			if(tasklist.get(i).getTitle().equals(title)&& tasklist.get(i).getUserName().equals(username)){
				task=tasklist.get(i);
				task.stop();
				tasklist.remove(i);
				return true;
			}
		}
		ErrorInformation.set("��������δ��ʼ��");
		return false;
	}
	public static void main(String args[]){
		String thisMod1 = "1 2015-12-12 20:13:00";
		String thatMod1 = "1 2268323136@qq.com bjm0805 1870354413@qq.com testlll ����webiftttTest";
		//TaskList.newTask("����1",thisMod1, thatMod1);
	}
}
