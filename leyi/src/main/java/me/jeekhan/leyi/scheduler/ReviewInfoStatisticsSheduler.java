package me.jeekhan.leyi.scheduler;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.jeekhan.leyi.service.MailSendService;

@Component
public class ReviewInfoStatisticsSheduler {
	private String prop_file = "props/mail_tpl";
	private ResourceBundle BUNDLE = ResourceBundle.getBundle(prop_file);
    
	@Autowired
	private MailSendService mailSendService;
	
	/**
	 * ��Ӵ������Ϣ�����ʼ���¼
	 */
	@Scheduled(cron="0 54 * * * ? ")
	public void get4ReviewInfo(){
		System.out.println("��Ӵ������Ϣ�����ʼ���¼");
		mailSendService.add4ReviewWarn();
	}
	
	
	/**
	 * ����ָ��key��ȡ��Ӧ��ֵ
	 * @param key
	 * @param params
	 * @return
	 */
	public String getForamtValue(String key,Object[] params) {
		String keyValue = null;
		String msg = null;
		try{
			keyValue = BUNDLE.getString(key);
			MessageFormat mf = new MessageFormat(keyValue); 
			msg = mf.format(params);  
		}catch(Exception e){
			return null;
		}
		return msg;
	}

}
