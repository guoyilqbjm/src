package Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import database.GetUserInformation;
import error.ErrorInformation;

public class TaskList {
	public static ArrayList<Task> tasklist = new ArrayList<Task>();
	private static String geToken = "2.00obC7IDbjEJzD007b764f090pCAEe";
	private static String guoToken = "2.00obC7IDbjEJzD007b764f090pCAEe";

	private final static int DEPOSIT = 10;

	public static ArrayList<Task> getTaskList() {
		return tasklist;
	}

	static class StartTask extends TimerTask {
		private Task task;

		public StartTask(Task newTask) {
			task = newTask;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			task.run();
			if (task.getState() == 1) {
				for (int i = 0; i < tasklist.size(); ++i) {
					if (tasklist.get(i).getTitle().equals(task.getTitle())
							&& tasklist.get(i).getUserName().equals(task.getUserName())) {
						task = tasklist.get(i);
						task.stop();
						tasklist.remove(i);
						ErrorInformation.set("该任务不满足条件，无法运行！");
					}
				}
			}
		}

	}

	private static Date getDate(String s) {
		/* 此处传入的时间格式为：####-##-## ##:##:## */
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		String tempdata[] = s.split(" ");
		String data[] = tempdata[0].split("-");
		String time[] = tempdata[1].split(":");
		calendar.set(Integer.parseInt(data[0]), Integer.parseInt(data[1]) - 1, Integer.parseInt(data[2]), // 设置年月日
				Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));// 设置时分秒
		return calendar.getTime();
	}

	private static Task newTask(String username, String title, String thisMod, String thatMod) {
		Task newTask = new Task();
		newTask.setTitle(title);
		newTask.setUserName(username);
		String thisMods[] = thisMod.split(",");
		String thatMods[] = thatMod.split(",");
		newTask.setThisType(Integer.parseInt(thisMods[0]));// 为了设置开始任务时候的时间间隔

		String userinfo = GetUserInformation.get(username);
		String infors[] = userinfo.split(",");
		assert(infors.length==4);
		int level = Integer.parseInt(infors[3]);
		int deposit = DEPOSIT - level;
		if (Integer.parseInt((GetUserInformation.get(username).split(","))[1]) < deposit) {
			ErrorInformation.set("用户余额不足，无法开始任务！");
			ErrorInformation.setBasePage("managetasks.jsp");
			return null;
		}
		newTask.setDeposit(-deposit);
		if (thisMods[0].equals("1")) {
			newTask.addThisTask1(getDate(thisMods[1]));

		} else if (thisMods[0].equals("2")) {
			newTask.addThisTask2(thisMods[1], thisMods[2]);
		} else if (thisMods[0].equals("3")) {
			newTask.addThisTask3(guoToken, getDate(thisMods[3]));
		} else if (thisMods[0].equals("4")) {
			newTask.addThisTask4(geToken, thisMods[3]);
		}
		if (thatMods[0].equals("1")) {
			String minfo[] = new String[6];
			minfo[0] = thatMods[1];
			minfo[1] = thatMods[2];
			minfo[2] = thatMods[3];
			minfo[3] = thatMods[1];
			minfo[4] = thatMods[4];
			minfo[5] = thatMods[5];
			newTask.addThatTask1(minfo);
		} else if (thatMods[0].equals("2")) {
			String infor[] = new String[3];
			infor[0] = thatMods[1];
			infor[1] = thatMods[2];
			infor[2] = thatMods[3];
			newTask.addThatTask2(infor, geToken);
		}
		return newTask;
	}

	public static boolean startTask(String info[]) {

		assert (info.length == 4);

		String username = info[0], title = info[1], thisMode = info[2], thatMode = info[3];
		assert (username != null && title != null && thisMode != null && thatMode != null);
		Task task = null;
		for (int i = 0; i < tasklist.size(); ++i) {
			if (tasklist.get(i).getTitle().equals(title) && tasklist.get(i).getUserName().equals(username)) {
				ErrorInformation.set("任务已经开始，无法再次开始！");
				return false;
			}
		}
		task = newTask(username, title, thisMode, thatMode);
		if(task==null){
			return false;
		}
		tasklist.add(task);

		Timer timer = new Timer();
		task.setTimer(timer);
		task.setState(0);
		int thisType = task.getThisType();
		if (thisType == 1 || thisType == 3)
			timer.schedule(new StartTask(task), 0, 1000);
		else if (thisType == 2)
			timer.schedule(new StartTask(task), 0, 5000);
		else
			timer.schedule(new StartTask(task), 0, 20000);
		return true;
	}

	public static boolean stopTask(String username, String title) {
		Task task = null;
		for (int i = 0; i < tasklist.size(); ++i) {
			if (tasklist.get(i).getTitle().equals(title) && tasklist.get(i).getUserName().equals(username)) {
				task = tasklist.get(i);
				task.stop();
				tasklist.remove(i);
				return true;
			}
		}
		ErrorInformation.set("该任务尚未开始！");
		return false;
	}

	public static void main(String args[]) {
		String thisMod1 = "1 2015-12-12 20:13:00";
		String thatMod1 = "1 2268323136@qq.com bjm0805 1870354413@qq.com testlll 我是webiftttTest";
		// TaskList.newTask("程序1",thisMod1, thatMod1);
	}
}
