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
	/*���ص�ǰThis�������й����е�ʵʱ����*/
	public abstract String getSummary();
	/*������������ʱThis���������*/
	public abstract int runTest();
	/*������Ӧ��This�����ҷ���This�������н��
	 * ����ֵΪ-1ʱ��ʾ��������Ϊ0��ʾThis���������㣬
	 * Ϊ1ʱ��ʾThis�����Ѿ�����;*/
}

class SetClock extends ThisTask{
	private String information;
	private String summary;
	private Date date;
	
	public SetClock(Date newDate){
		this.date = (Date)newDate.clone();
		summary = "ʱ�䣺"+date;
		information="�趨ʱ��Ϊ��"+date;
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
			information = "�趨ʱ���ڵ�ǰʱ��֮ǰ���޷�ִ������";
			return -1;
		}
		if(time1.compareTo(time2)==0){
			information = "ʱ�䵽~";
			return 1;
		}
		else{
			long count = date.getTime()-temp.getTime();
			information = "�����趨ʱ�仹��"+count/1000+"��";
			
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
		summary = "���䣺"+userName+"�յ��ʼ�ʱ��";
		information="���仹û���յ��ʼ�!";
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
			information = "�������Ӵ���";
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
        //emailSession.setDebug(true);//�Ƿ�ʼdebugģʽ

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
			information = "���Ӵ��������û���������������������ã�";
			return false;
		}
		return true;
	}
   public int fetch() throws MessagingException{
	   if(lastLength==-1){//��ʾû�����ӹ�����Ҫ��������
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
    	   information="����û���յ��ʼ�����������"+s;
    	   times=times%3+1;
    	   return 0;
       }
       else{
    	   lastLength=length;
    	   information="���䣺"+userName+"�յ�һ���µ��ʼ��ˡ�";
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
		summary="���access_tokenΪ"+access_token+"���˺��Ƿ�ᷢ���ݰ���\""+pattern+"\"��΢��";
		information="΢�����ݼ���Ѿ����óɹ���";
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
						information="acess_tokenΪ"+access_token+"���˺��Ѿ�������΢����΢������Ϊ��\""+pre.getText()+"\"";
						return 1;
					}
				}
				information="access_tokenΪ"+access_token+"���˺��Ѿ�������΢������������ָ��������";
				return 0;
			} else {
				information="access_tokenΪ"+access_token+"���˺Ż�û�и���΢��";
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
		this.summary="���access_tokenΪ"+access_token+"��"+ddline+"֮ǰ�Ƿ����΢��";
		information="���΢�����óɹ�";
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
					information="accesss_tokenΪ"+access_token+"���˺��Ѿ�������΢����";
					return 1;
				} else {
					information="access_tokenΪ"+access_token+"���˺�û�и���΢����";
					return 0;
				}

			} catch (WeiboException e) {
				e.printStackTrace();
				return 0;
			}
		}
		else{
			information="ʱ��δ����";
			return 0;
		}
	}
	
}