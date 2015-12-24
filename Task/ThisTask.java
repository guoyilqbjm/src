package Task;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;


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