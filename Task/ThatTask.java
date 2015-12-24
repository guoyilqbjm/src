package Task;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public abstract class ThatTask {
	public abstract String getInformation();
	public abstract String getSummary();
	public abstract boolean runResult();
	/*����that���񣬲��������н��
	 * Ϊtrueʱ��ʾthat�������гɹ�������ʧ��*/
}

class SendQQMail extends ThatTask{
	private String reciveName;
	private String sendName;
	private String content;
	private String userName;
	private String subject;
	private String password;
	private String summary;
	private String information;
	
	public SendQQMail(){
		reciveName="";
		sendName = "";
		content = "";
		subject = "";
		userName = "";
		password = "";
		information = "�ʼ���δ����~";
		
	}
	
	public SendQQMail(String [] input){
		this.userName=input[0];
		this.password=input[1];
		this.reciveName=input[2];
		this.sendName=input[3];
		this.subject=input[4];
		this.content=input[5];
		information = "�ʼ���δ����~";
		summary = "������:"+sendName+"\n�ռ���:"+reciveName
				+ "\n����:  "+subject+"\n����:  "+content+'\n';
	}
	public String getInformation(){
		return information;
	}

	public String[] getMailInformation(){
		String result[] = new String[6];
		result[0]=this.userName;
		result[1]=this.password;
		result[2]=this.reciveName;
		result[3]=this.sendName;
		result[4]=this.subject;
		result[5]=this.content;
		return result;
	}
	
	public String getSummary(){
		return summary;
	}

	public boolean runResult(){
		try {
			sendMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public SendQQMail(String reciveName,String sendName,String content,String subject){
		this.reciveName  = reciveName;
		this.sendName = sendName;
		this.content = content;
		this.subject = subject;
	}
	
	public void setUser(String userName,String password){
		this.userName = userName;
		this.password = password;
	}
	private MimeMessage createTextMail(Session session) throws Exception{
		MimeMessage message = new MimeMessage(session);//�����ʼ�����
		message.setFrom(new InternetAddress(sendName));//���÷�����
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciveName));//�����ռ���
		message.setSubject(subject);
		message.setContent(content,"text/html;charset=UTF-8");
		return message;
	}
	
	private void sendMail() throws Exception{
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(prop);//����session
		session.setDebug(true);//����session��debugģʽ
		Transport ts =session.getTransport();//�õ�transport����
		ts.connect("smtp.qq.com",userName,password);
		Message message = createTextMail(session);
		ts.sendMessage(message, message.getAllRecipients());
		information = "�ʼ��ѷ���~";
		 ts.close();
	}
}

/*
class SendWeibo extends ThatTask{
	 private String information;
	 private String summary;
	 private String userName;
	 private String password;
	 private String content;
	 public SendWeibo(String userName,String password,String content){
		 this.userName = userName;
		 this.password = password;
		 this.content = content;
		 summary = "�û���"+userName+"\n΢�����ݣ�"+content;
		 information="΢����û�з��ͣ��ȴ�������";
		 }
	 public String[] getAllInformation(){
		 String s[] = new String [3];
		 s[0]=this.userName;
		 s[1]=this.password;
		 s[2]=this.content;
		 return s;
		 }
	 public String getInformation() {
			// TODO Auto-generated method stub
		 return information;
		}
	 public String getSummary() {
			// TODO Auto-generated method stub
		 return summary;
	 }
	 public boolean runResult() {
		 return sendWeibo();
	 }
	 private boolean sendWeibo(){
		 String []s = new String[1];
		 s[0]=content;
		 try {
			UpdateStatus.main(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			information = "΢������ʧ�ܣ�";
			return false;
		}
		 information="΢�����ͳɹ���";
		 return true;
    }
}
*/
