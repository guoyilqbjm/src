package Task;

import java.util.Date;
import java.util.Timer;

import database.AddOneTransaction;
import database.GetUserInformation;

public class Task {
	private String title;// 任务标题
	private String username;// 任务使用者
	private ThisTask thisTask = null;// this任务的引用，
	private ThatTask thatTask = null;// that任务的引用
	private int state = 1;
	/* 表示程序状态：0 正在运行中；1 已停止 */
	private String summary = "";
	/* 任务信息的陈述：由this任务的描述、that任务的描述组成 */
	private String information = "";
	/* 当前任务的运行描述：由this任务的描述、当前整体任务状态描述、that任务的描述三者组成 */
	private Timer timer;// 存储当前任务被调度的Timer对象，用于随时停止当前任务
	private int thisType = 1;

	private int length = 1;
	private int deposit;
	
	
	private String getss() {
		String s = "";
		for (int i = 0; i < length; ++i)
			s += '.';
		length = length % 3 + 1;
		return s;
	}

	public void setUserName(String str) {
		this.username = str;
	}

	public String getUserName() {
		return this.username;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}// 将调度该任务的timer传入，方便随时停止当前任务。

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		this.summary = thisTask.getSummary() + '\n' + thatTask.getSummary();
		return summary;
	}

	public String getInformation() {
		return thisTask.getInformation() + '\n' + information + "\n" + thatTask.getInformation();
	}

	public String getTitle() {
		return title;
	}

	public void addThisTask1(Date date) {
		thisTask = new SetClock(date);
	}

	public void addThisTask2(String userName, String password) {
		thisTask = new TestMail(userName, password);
	}

	public void addThisTask3(String access_token, Date date) {
		thisTask = new WeiBoTest(access_token, date);
	}

	public void addThisTask4(String access_token, String pattern) {
		thisTask = new WeiBoContentTest(access_token, pattern);
	}

	public void addThatTask1(String[] mailInformation) {
		thatTask = new SendQQMail(mailInformation);
	}

	public void addThatTask2(String[] weiboInformation, String access_token) {
		thatTask = new SendWeibo(weiboInformation[0], weiboInformation[1], access_token, weiboInformation[2]);
	}

	public ThisTask getThisTask() {
		return thisTask;
	}

	public ThatTask getThatTask() {
		return thatTask;
	}

	public void run() {// 运行task
		int r = thisTask.runTest();
		if (r == 1) {
			information = "This条件满足，已经执行that事件";
			thatTask.runResult();
			timer.cancel();
			AddOneTransaction.add(username,deposit);
			state=1;
		}
		information="This程序执行中...";
	}

	public void pause() {// 暂停task
		if (state == 0) {
			timer.cancel();
			state = 1;
			information = "This程序被暂停！";
		}
	}

	public void stop() {// 停止task
		information = "This程序被停止！";
		if(state==0)
			timer.cancel();
	}

	public int getThisType() {
		return thisType;
	}

	public void setThisType(int thisType) {
		this.thisType = thisType;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
}
