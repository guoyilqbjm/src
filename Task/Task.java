package Task;

import java.util.Date;
import java.util.Timer;

public class Task 
{
	private String title;//�������
	private String username;//����ʹ����
	private ThisTask thisTask=null;//this��������ã�
	private ThatTask thatTask=null;//that���������
	private int state = 1;
	/*��ʾ����״̬��0 ���������У�1 ��ֹͣ*/
	private String summary = "";
	/*������Ϣ�ĳ�������this�����������that������������*/
	private String information="";
	/*��ǰ�����������������this�������������ǰ��������״̬������that����������������*/
	private Timer timer;//�洢��ǰ���񱻵��ȵ�Timer����������ʱֹͣ��ǰ����
	
	
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
	}//�����ȸ������timer���룬������ʱֹͣ��ǰ����
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
	public void run(){//����task
		int r = thisTask.runTest();
		if(r==1){
			information="This�������㣬�Ѿ�ִ��that�¼�";
			thatTask.runResult();
			if(thisTask instanceof SetClock){
				state = 1;
			}
		}
		/*�����ж�state�Ƿ�Ϊ0��ֻҪ��Ϊ���жϵ�ǰ״̬�Ƿ���ֹͣ״̬�����ǣ������޸������Ϣ*/
		else if(r==-1 && state == 0){
			information="This���������㣬�޷�ִ��That�¼�";
			state = 1;
		}
		else if (r == 0 && state == 0){
			information = "This����������"+getss()+"��";
		}
		
	}
	public void pause(){//��ͣtask
		if(state==0){
			timer.cancel();
			state = 1;
			information="This������ͣ��";
		}
	}
	public void stop(){//ֹͣtask
		information="This����ֹͣ��";
		if(state==0)
			timer.cancel();
	}
}
