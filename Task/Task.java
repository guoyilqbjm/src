package Task;

import java.util.Date;
import java.util.Timer;

public class Task 
{
	private String title;//任务标题
	private String username;//任务使用者
	private ThisTask thisTask=null;//this任务的引用，
	private ThatTask thatTask=null;//that任务的引用
	private int state = 1;
	/*表示程序状态：0 正在运行中；1 已停止*/
	private String summary = "";
	/*任务信息的陈述：由this任务的描述、that任务的描述组成*/
	private String information="";
	/*当前任务的运行描述：由this任务的描述、当前整体任务状态描述、that任务的描述三者组成*/
	private Timer timer;//存储当前任务被调度的Timer对象，用于随时停止当前任务
	
	
	private int length = 1;
	private String getss(){
		String s="";
		for(int i=0;i<length;++i)
			s+='.';
		length=length%3+1;
		return s;
	}
	
	public void setUserName(String str){
		this.username = str;
	}
	
	public String getUserName(){
		return this.username;
	}
	
	public void setTimer(Timer timer){
		this.timer = timer;
	}//将调度该任务的timer传入，方便随时停止当前任务。
	public void setState(int state){
		this.state = state;
	}
	public int getState(){
		return state;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getSummary(){
		this.summary = thisTask.getSummary() + '\n'+thatTask.getSummary();
		return summary;
	}
	public String getInformation(){
		return thisTask.getInformation()+'\n'+information+"\n"+thatTask.getInformation();
	}
	
	public String getTitle(){
		return title;
	}
	public void addThisTask1(Date date){
		thisTask = new SetClock(date);
	}
	public void addThisTask2(String userName,String password){
		thisTask = new TestMail(userName,password);
	}
	public void addThatTask1(String [] mailInformation){
		thatTask = new SendQQMail(mailInformation);
	}
	public void addThatTask2(String []weiboInformation){
		//thatTask = new SendWeibo(weiboInformation[0],weiboInformation[1],weiboInformation[2]);
	}
	public ThisTask getThisTask(){
		return thisTask;
	}
	public ThatTask getThatTask(){
		return thatTask;
	}
	public void run(){//运行task
		int r = thisTask.runTest();
		if(r==1){
			information="This条件满足，已经执行that事件";
			thatTask.runResult();
			if(thisTask instanceof SetClock){
				state = 1;
			}
		}
		/*增加判断state是否为0，只要是为了判断当前状态是否是停止状态，若是，则不用修改输出信息*/
		else if(r==-1 && state == 0){
			information="This条件不满足，无法执行That事件";
			state = 1;
		}
		else if (r == 0 && state == 0){
			information = "This程序运行中"+getss()+"！";
		}
		
	}
	public void pause(){//暂停task
		if(state==0){
			timer.cancel();
			state = 1;
			information="This程序被暂停！";
		}
	}
	public void stop(){//停止task
		information="This程序被停止！";
		if(state==0)
			timer.cancel();
	}
}
