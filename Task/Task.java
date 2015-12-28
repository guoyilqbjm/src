package Task;

import java.util.Date;
import java.util.Timer;

import database.AddOneTransaction;
import database.GetUserInformation;

public class Task {
	private String title;// �������
	private String username;// ����ʹ����
	private ThisTask thisTask = null;// this��������ã�
	private ThatTask thatTask = null;// that���������
	private int state = 1;
	/* ��ʾ����״̬��0 ���������У�1 ��ֹͣ */
	private String summary = "";
	/* ������Ϣ�ĳ�������this�����������that������������ */
	private String information = "";
	/* ��ǰ�����������������this�������������ǰ��������״̬������that���������������� */
	private Timer timer;// �洢��ǰ���񱻵��ȵ�Timer����������ʱֹͣ��ǰ����
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
	}// �����ȸ������timer���룬������ʱֹͣ��ǰ����

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

	public void run() {// ����task
		int r = thisTask.runTest();
		if (r == 1) {
			information = "This�������㣬�Ѿ�ִ��that�¼�";
			thatTask.runResult();
			timer.cancel();
			AddOneTransaction.add(username,deposit);
			state=1;
		}
		information="This����ִ����...";
	}

	public void pause() {// ��ͣtask
		if (state == 0) {
			timer.cancel();
			state = 1;
			information = "This������ͣ��";
		}
	}

	public void stop() {// ֹͣtask
		information = "This����ֹͣ��";
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
