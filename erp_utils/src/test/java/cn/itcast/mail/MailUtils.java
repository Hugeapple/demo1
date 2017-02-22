package cn.itcast.mail;

import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {
	public static void main(String[] args) throws Exception {
		
		
		Properties props = new Properties();
		
		props.put("mail.stmp.host", "smtp.163.com");
		
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(props);
		
		MimeMessage mimeMessage = new MimeMessage(session);
		
		InternetAddress address = new InternetAddress("13122680069@163.com");
		
		mimeMessage.setFrom(address);
		
		InternetAddress toAddress = new InternetAddress("daizhibiao0515@126.com");
		
		mimeMessage.setRecipient(RecipientType.TO, toAddress);
		
		mimeMessage.setSubject("mdzz");
		
		mimeMessage.setText("你搞不搞基么？？？？？？？？？？？？？？？？？？？？？");
		mimeMessage.saveChanges();
		//7 发送:坐火箭
		Transport transport = session.getTransport("smtp");
		//登录邮件
		transport.connect("smtp.163.com", "13122680069@163.com", "xt1085388230");
		//第一个参数：邮件内容
//		/第二个参数：所有的接收者
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		//关闭火箭
		transport.close();
		
		System.out.println("发送成功");
	}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

