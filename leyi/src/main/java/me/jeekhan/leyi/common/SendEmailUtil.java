package me.jeekhan.leyi.common;
import java.util.Properties;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart; 
import javax.mail.Message; 
import javax.mail.MessagingException; 
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.jeekhan.leyi.scheduler.SendMailSheduler; 

public class SendEmailUtil {
	private static Logger log = LoggerFactory.getLogger(SendEmailUtil.class);
	private static String username = "zhihuang_2009@163.com";
	private static String password = "zhaolongbo1248";
	private static String host = "smtp.163.com";
	private static String port = "25";
	
	public static void main(String[] args){
		List<String> addresses = new ArrayList<String>();
		addresses.add("1079946866@qq.com");
		
		List<String> ccAddr = new ArrayList<String>();
		ccAddr.add("zhihuang_2008@sina.com");
		
		List<String> bccAddr = new ArrayList<String>();
		bccAddr.add("aoksen@hotmail.com");
		
		List<File> attachFiles = new ArrayList<File>();
		File file = new File("D:/123/���Խ��");
		File file2 = new File("C:/Users/Jee Khan/Pictures/����СͼƬ.zip");
		attachFiles.add(file);
		attachFiles.add(file2);
		sendMail("һ������ʼ�","�����Ƿ��ͳɹ�99999999999",addresses,ccAddr,bccAddr,attachFiles);
	} 
	/**
	 * 
	 * @param subject	�ʼ�����	
	 * @param content	�ʼ�����
	 * @param toAddr	�ռ����б�
	 * @param ccAddr	�����б�
	 * @param bccAddr	�����б� 
	 * @param attachFiles	�����б��ļ�������Ŀ¼���пɶ�Ȩ�ޣ�
	 * @return
	 */
	public static String sendMail(String subject,String content,List<String> toAddr,List<String> ccAddr,List<String> bccAddr,List<File> attachFiles){
		if(subject == null || content == null || subject.trim().length()<1 || content.trim().length()<1 || toAddr == null || toAddr.isEmpty()){
			return "�ʼ�Ҫ�أ����⡢���ݡ��ռ��ˣ���������";
		}
		//�������Ҫ�������ʼ�
		MailSenderInfo mailInfo = new MailSenderInfo(); 
		mailInfo.setMailServerHost(host); 
		mailInfo.setMailServerPort(port); 
		mailInfo.setValidate(true); 
		mailInfo.setUserName(username); 
		mailInfo.setPassword(password);//������������ 
		mailInfo.setFromAddress(username); 
		mailInfo.setToAddresses(toAddr); 
		mailInfo.setCcAddresses(ccAddr);
		mailInfo.setBccAddresses(bccAddr);
		mailInfo.setSubject(subject); 
		mailInfo.setContent(content); 
		mailInfo.setAttachFiles(attachFiles);
		//�������Ҫ�������ʼ�
		return sendHtmlMail(mailInfo);//����html��ʽ
	}
	
	/** 
	  * ��HTML��ʽ�����ʼ� 
	  * @param mailInfo �����͵��ʼ���Ϣ 
	  * @return 00-�ɹ�
	  */ 
	private static String sendHtmlMail(MailSenderInfo mailInfo){ 
		// �ж��Ƿ���Ҫ�����֤ 
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		//�����Ҫ�����֤���򴴽�һ��������֤��  
		if (mailInfo.isValidate()) { 
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		} 
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session 
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
		try { 
			// ����session����һ���ʼ���Ϣ 
			Message mailMessage = new MimeMessage(sendMailSession); 
			// �����ʼ������ߵ�ַ 
			Address from = new InternetAddress(mailInfo.getFromAddress()); 
			// �����ʼ���Ϣ�ķ����� 
			mailMessage.setFrom(from); 
			// �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ�� 
			InternetAddress[] addresses = new InternetAddress[mailInfo.getToAddresses().size()];  
			int i=0;
			for (String addr : mailInfo.getToAddresses()) {  
				addresses[i++] = new InternetAddress(addr);  
			} 
			// Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO 
			mailMessage.setRecipients(Message.RecipientType.TO,addresses); 
			//�����ʼ����͵�ַ
			if(mailInfo.getCcAddresses()!=null && !mailInfo.getCcAddresses().isEmpty()){
				i=0;
				InternetAddress[] ccAddr = new InternetAddress[mailInfo.getCcAddresses().size()];
				for (String addr : mailInfo.getCcAddresses()) {  
					ccAddr[i++] = new InternetAddress(addr);  
				}
				mailMessage.setRecipients(Message.RecipientType.CC,ccAddr);
			}
			//�������͵�ַ
			if(mailInfo.getBccAddresses()!=null && !mailInfo.getBccAddresses().isEmpty()){
				i=0;
				InternetAddress[] bccAddr = new InternetAddress[mailInfo.getBccAddresses().size()];
				for (String addr : mailInfo.getBccAddresses()) {  
					bccAddr[i++] = new InternetAddress(addr);  
				}
				mailMessage.setRecipients(Message.RecipientType.BCC,bccAddr);
			}
			// �����ʼ���Ϣ������ 
			mailMessage.setSubject(mailInfo.getSubject()); 
			// �����ʼ���Ϣ���͵�ʱ�� 
			mailMessage.setSentDate(new Date()); 
			// MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ��� 
			Multipart multiPart = new MimeMultipart(); 
			// ����һ������HTML���ݵ�MimeBodyPart 
			BodyPart html = new MimeBodyPart(); 
			// ����HTML���� 
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8"); 
			multiPart.addBodyPart(html); 
			// ��MiniMultipart��������Ϊ�ʼ����� 
			mailMessage.setContent(multiPart); 
			// ��Ӹ���������
			if (mailInfo.getAttachFiles()!= null && !mailInfo.getAttachFiles().isEmpty()) {
				for(File attachment : mailInfo.getAttachFiles()){
					BodyPart attachmentBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(attachment);
					attachmentBodyPart.setDataHandler(new DataHandler(source));
					// �������Ҫ��ͨ�������Base64�����ת�����Ա�֤������ĸ����������ڷ���ʱ����������
					//sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
					//messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
					//MimeUtility.encodeWord���Ա����ļ�������
					attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
					multiPart.addBodyPart(attachmentBodyPart);
				}
			}
			/*// �����ʼ���Ϣ����Ҫ���� :ʹ���ı�
			String mailContent = mailInfo.getContent(); 
			mailMessage.setText(mailContent);*/
			// �����ʼ� 
			Transport.send(mailMessage); 
			return "00"; 
		} catch (MessagingException | UnsupportedEncodingException ex) { 
			return ex.getMessage();
		} 
	} 
} 

   /**
      ������֤��
   */
  class MyAuthenticator extends Authenticator{
 	String userName=null;
 	String password=null;
 	 
 	public MyAuthenticator(){
 	}
 	public MyAuthenticator(String username, String password) { 
 		this.userName = username; 
 		this.password = password; 
 	} 
 	@Override
 	protected PasswordAuthentication getPasswordAuthentication(){
 		return new PasswordAuthentication(userName, password);
 	}
 }
  
/** 
* �����ʼ���Ҫʹ�õĻ�����Ϣ 
*/ 
class MailSenderInfo { 
	
	// �����ʼ��ķ�������IP�Ͷ˿� 
	private String mailServerHost; 
	private String mailServerPort = "25"; 
	
	// �ʼ������ߵĵ�ַ 
	private String fromAddress; 
	
	// �ʼ������ߵĵ�ַ 
	private List<String> toAddresses; 
	// �ʼ����͵ĵ�ַ 
	private List<String> ccAddresses; 
	// �ʼ����͵ĵ�ַ 
	private List<String> bccAddresses; 
	
	// ��½�ʼ����ͷ��������û��������� 
	private String userName; 
	private String password; 
	
	// �Ƿ���Ҫ�����֤ 
	private boolean validate = false; 
	
	// �ʼ����� 
	private String subject; 
	
	// �ʼ����ı����� 
	private String content; 
	
	// �ʼ�����
	private List<File> attachFiles; 	
	/** 
	  * ����ʼ��Ự���� 
	  */ 
	public Properties getProperties(){ 
	  Properties p = new Properties(); 
	  p.put("mail.smtp.host", this.mailServerHost); 
	  p.put("mail.smtp.port", this.mailServerPort); 
	  p.put("mail.smtp.auth", validate ? "true" : "false"); 
	  return p; 
	} 
	public String getMailServerHost() { 
	  return mailServerHost; 
	} 
	public void setMailServerHost(String mailServerHost) { 
	  this.mailServerHost = mailServerHost; 
	}
	public String getMailServerPort() { 
	  return mailServerPort; 
	}
	public void setMailServerPort(String mailServerPort) { 
	  this.mailServerPort = mailServerPort; 
	}
	public boolean isValidate() { 
	  return validate; 
	}
	public void setValidate(boolean validate) { 
	  this.validate = validate; 
	}

	public List<File> getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(List<File> attachFiles) {
		this.attachFiles = attachFiles;
	}
	
	public String getFromAddress() { 
	  return fromAddress; 
	} 
	public void setFromAddress(String fromAddress) { 
	  this.fromAddress = fromAddress; 
	}
	public String getPassword() { 
	  return password; 
	}
	public void setPassword(String password) { 
	  this.password = password; 
	}
	public List<String> getToAddresses() { 
	  return toAddresses; 
	} 
	public void setToAddresses(List<String> toAddresses) { 
	  this.toAddresses = toAddresses; 
	} 
	
	public List<String> getCcAddresses() {
		return ccAddresses;
	}
	public void setCcAddresses(List<String> ccAddresses) {
		this.ccAddresses = ccAddresses;
	}
	public List<String> getBccAddresses() {
		return bccAddresses;
	}
	public void setBccAddresses(List<String> bccAddresses) {
		this.bccAddresses = bccAddresses;
	}
	public String getUserName() { 
	  return userName; 
	}
	public void setUserName(String userName) { 
	  this.userName = userName; 
	}
	public String getSubject() { 
	  return subject; 
	}
	public void setSubject(String subject) { 
	  this.subject = subject; 
	}
	public String getContent() { 
	  return content; 
	}
	public void setContent(String textContent) { 
	  this.content = textContent; 
	} 
} 