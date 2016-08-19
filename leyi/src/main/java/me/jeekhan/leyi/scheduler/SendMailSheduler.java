package me.jeekhan.leyi.scheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.jeekhan.leyi.common.SendEmailUtil;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.model.MailSendInfo;
import me.jeekhan.leyi.service.MailSendService;

@Component
public class SendMailSheduler {
	Logger log = LoggerFactory.getLogger(SendMailSheduler.class);
	private String bathPath4AttachFils = SysPropUtil.getParam("DIR_ATTACHFILE");
	@Autowired
	private MailSendService mailSendService;
	
	/**
	 * �����ʼ�
	 */
	@Scheduled(cron="0 55 * * * ? ")
	public void BatchSendEmails(){
		
		List<MailSendInfo> mails = mailSendService.getMails4Send();
		if(mails == null || mails.isEmpty()){
			return;
		}
		log.info("=====�������ʼ����Ϳ�ʼ=====");
		for(MailSendInfo mail:mails){
			String subject = mail.getSubject();
			String content = mail.getContent();
			String toAddr = mail.getToAddr();
			String status = mail.getStatus();
			int sendNum = mail.getSendNum();
			if("1".equals(status) || "F".equals(status) && sendNum <3){
				if(subject == null || subject.trim().length()<1 || 
						content == null || content.trim().length()<1 || 
						toAddr == null || toAddr.trim().length()<1 ){
					mail.setSendNum(4);
					mail.setSendTime(new Date());
					mail.setStatus("F");
					mail.setFailMsg("���⡢���ݡ��ռ��˲���Ϊ�գ�");
					mailSendService.updateMailInfo(mail);
					return;
				}else{
					sendOneMail(mail);
				}
			}else{
				return;
			}
		}
		log.info("=====�������ʼ����ͽ���=====");
	}
	
	private void sendOneMail(MailSendInfo mail){
		String regexp = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		StringBuffer failMsg = new StringBuffer("");
		String toAddr = mail.getToAddr();
		String ccAddr = mail.getCcAddr();
		String bccAddr = mail.getBccAddr();
		String filenames = mail.getAttachfileNames();
		List<String> listToAddr = new ArrayList<String>();
		for(String str:toAddr.split(",")){
			str = str.trim();
			if(str.matches(regexp)){
				listToAddr.add(str);
			}else if(str.trim().length()>0){
				failMsg.append("�ռ��˵�ַ��" + str + "����ʽ����ȷ��") ;
			}
		}
		List<String> listCCAddr = null;
		if(ccAddr!=null && ccAddr.trim().length()>0){
			listCCAddr = new ArrayList<String>();
			for(String str:ccAddr.split(",")){
				str = str.trim();
				if(str.matches(regexp)){
					listCCAddr.add(str);
				}else if(str.trim().length()>0){
					failMsg.append("���͵�ַ��" + str + "����ʽ����ȷ��") ;
				}
			}
		}
		
		List<String> listBCCAddr = null;
		if(bccAddr!=null && bccAddr.trim().length()>0){
			listBCCAddr = new ArrayList<String>();
			for(String str:bccAddr.split(",")){
				str = str.trim();
				if(str.matches(regexp)){
					listBCCAddr.add(str);
				}else if(str.trim().length()>0){
					failMsg.append("���͵�ַ��" + str + "����ʽ����ȷ��") ;
				}
			}
		}

		
		List<File> attachFiles = null;
		if(filenames !=null && filenames.length()>0){
			attachFiles = new ArrayList<File>();
			for(String name:filenames.split(",")){
				File file = new File(bathPath4AttachFils + mail.getId() + "/" + name);
				if(file.exists() && !file.isDirectory() && file.canRead()){
					attachFiles.add(file);
				}else if(name.trim().length()>0){
					failMsg.append("������Ϊ��" + name + "���ɶ�����ͨ�ļ�����Ŀ¼����");
				}
			}
		}
		//�ռ��˵�ַΪ��
		if(listToAddr == null || listToAddr.isEmpty()){
			mail.setSendNum(4);
			mail.setSendTime(new Date());
			mail.setStatus("F");
			if(failMsg.length()<1){
				failMsg.append("�ռ��˵�ַ��ʽ����ȷ(Ϊ��)��");
			}
			mail.setFailMsg(failMsg.toString());
			mailSendService.updateMailInfo(mail);
		}else{
			String result = SendEmailUtil.sendMail(mail.getSubject(), mail.getContent(), listToAddr, listCCAddr, listBCCAddr, attachFiles);
			mail.setSendTime(new Date());
			mail.setSendNum(mail.getSendNum() + 1);
			if("00".equals(result)){
				mail.setStatus("0");
			}else{
				mail.setStatus("F");
				mail.setFailMsg(result + "\n" + failMsg.toString());
			}
			mailSendService.updateMailInfo(mail);
		}
	}

}
