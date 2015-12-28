package Task;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

public abstract class ThisTask {
	public abstract String getInformation();
	/*返回当前This任务运行过程中的实时描述*/
	public abstract String getSummary();
	/*返回任务设置时This任务的描述*/
	public abstract int runTest();
	/*运行相应的This任务并且返回This任务运行结果
	 * 返回值为-1时表示遇到错误，为0表示This条件不满足，
	 * 为1时表示This条件已经满足;*/
}

class SetClock extends ThisTask{
	private String information;
	private String summary;
	private Date date;
	
	public SetClock(Date newDate){
		this.date = (Date)newDate.clone();
		summary = "时间："+date;
		information="设定时间为："+date;
	}
	public Date getDate(){
		return date;
	}
	public String getInformation(){
		return information;
	}
	public String getSummary(){
		return summary;
	}
	public int runTest(){
		Calendar presentCalendar = Calendar.getInstance();
		Date temp = presentCalendar.getTime();
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String time1 = date_format.format(temp.getTime());
		String time2 = date_format.format(date.getTime());
		if(time1.compareTo(time2)>0){
			information = "设定时间在当前时间之前，无法执行任务！";
			return -1;
		}
		if(time1.compareTo(time2)==0){
			information = "时间到~";
			return 1;
		}
		else{
			long count = date.getTime()-temp.getTime();
			information = "距离设定时间还差"+count/1000+"秒";
			
			System.out.println(information);
			return 0;
		}
	}
}

class TestMail extends ThisTask{
	
	private String userName;
	private String password;
	private String information;
	private String summary;
	private int lastLength=-1;
	final private String host="pop.qq.com";
	final String mailStoreType = "pop3s";
	private Store store = null;
	private int times = 1;
	
	public TestMail(String userName,String password){
		this.userName=userName;
		this.password=password;
		summary = "邮箱："+userName+"收到邮件时！";
		information="邮箱还没有收到邮件!";
	}
	public String[] getAllInformation(){
		String []in = new String[6];
		in[0] = userName;
		in[1] = password;
		return in;
	}
	
	public String getInformation(){
		return information;
	}
	public String getSummary(){
		return summary;
	}
	@Override
	public int runTest() {
		int i=-1;
		try {
			i = fetch();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			information = "邮箱连接错误！";
		}
		return i;
	}
	
	private boolean connect(){
		Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);
        //emailSession.setDebug(true);//是否开始debug模式

        // create the POP3 store object and connect with the pop server
		try {
			store = emailSession.getStore("pop3s");
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        try {
			store.connect(host, userName, password);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			information = "连接错误，请检查用户名或者密码或者网络设置！";
			return false;
		}
		return true;
	}
   public int fetch() throws MessagingException{
	   if(lastLength==-1){//表示没有连接过，需要建立连接
			if(!connect()){
				return -1;
			}
	   }
	   int length=0;
       Folder emailFolder = store.getFolder("INBOX");
       emailFolder.open(Folder.READ_ONLY);

         // retrieve the messages from the folder in an array and print it
       Message[] messages = emailFolder.getMessages();
       length = messages.length;
       emailFolder.close(false);
       if(lastLength==-1){
    	   this.lastLength = length;
    	   return 0;
       }
       else if (lastLength == length){
    	   String s = "";
    	   for(int i=0;i<times;++i)
    		   s += ".";
    	   information="邮箱没有收到邮件！邮箱检测中"+s;
    	   times=times%3+1;
    	   return 0;
       }
       else{
    	   lastLength=length;
    	   information="邮箱："+userName+"收到一份新的邮件了。";
    	   return 1;
       }
   }
}

class WeiBoContentTest extends ThisTask{
	private String pattern;
	private String access_token;
	private Date lastDate = new Date();
	private int lastCount = -1;
	private String summary="";
	private String information="";
	public WeiBoContentTest(String access_token,String pattern){
		this.pattern=pattern;
		this.access_token=access_token;
		summary="检测access_token为"+access_token+"的账号是否会发内容包含\""+pattern+"\"的微博";
		information="微博内容检测已经设置成功！";
	}
	
	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return information;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return summary;
	}

	@Override
	public int runTest() {
		// TODO Auto-generated method stub
		
		Timeline tm = new Timeline(access_token);
		try {
			StatusWapper status = tm.getUserTimeline();
			List<Status> all = status.getStatuses();
			Status pre = all.get(all.size() - 1);
			if (lastCount == -1)
				lastCount = all.size();
			Date preDate = pre.getCreatedAt();
			long space = preDate.getTime() - lastDate.getTime();
			if (space > 0) {
				for (int i = all.size() - 1; i >= lastCount - 1; i--) {
					pre = all.get(i);
					if (pre.getText().contains(pattern)) {
						lastDate = new Date();
						lastCount = all.size();
						information="acess_token为"+access_token+"的账号已经更新了微博，微博内容为：\""+pre.getText()+"\"";
						return 1;
					}
				}
				information="access_token为"+access_token+"的账号已经更新了微博，但不包含指定的内容";
				return 0;
			} else {
				information="access_token为"+access_token+"的账号还没有更新微博";
				return 0;
			}

		} catch (WeiboException e) {
			e.printStackTrace();
			return 0;
		}
	}
}

class WeiBoTest extends ThisTask{
	private String access_token;
	private Date lastDate = new Date();
	private Date ddline;
	private String information="";
	private String summary="";
	public WeiBoTest(String access_token,Date ddline){
		this.access_token = access_token;
		this.ddline = ddline;
		this.summary="检测access_token为"+access_token+"在"+ddline+"之前是否更新微博";
		information="检测微博设置成功";
	}
	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return information;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return summary;
	}

	@Override
	public int runTest() {
		// TODO Auto-generated method stu
		Date preDate = new Date();
		if(preDate.getTime()-ddline.getTime()<=60000 && preDate.getTime()-ddline.getTime()>=0){
			Timeline tm = new Timeline(access_token);
			try {
				StatusWapper status = tm.getUserTimeline();
				List<Status> all = status.getStatuses();
				Status pre = all.get(all.size() - 1);
				Date date = pre.getCreatedAt();
				long space = date.getTime() - lastDate.getTime();
				if (space > 0) {
					information="accesss_token为"+access_token+"的账号已经更新了微博！";
					return 1;
				} else {
					information="access_token为"+access_token+"的账号没有更新微博！";
					return 0;
				}

			} catch (WeiboException e) {
				e.printStackTrace();
				return 0;
			}
		}
		else{
			information="时间未到！";
			return 0;
		}
	}
	
}