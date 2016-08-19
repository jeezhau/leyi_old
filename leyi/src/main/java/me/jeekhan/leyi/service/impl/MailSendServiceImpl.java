package me.jeekhan.leyi.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.dao.MailSendInfoMapper;
import me.jeekhan.leyi.model.MailSendInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.MailSendService;
import me.jeekhan.leyi.service.ThemeClassService;
import me.jeekhan.leyi.service.UserService;

@Service
public class MailSendServiceImpl implements MailSendService{
	@Autowired
	private MailSendInfoMapper mailSendInfoMapper;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeClassService themeClassService;
	@Autowired
	private UserService userService;
	
	private String prop_file = "props/mail_tpl";
	private ResourceBundle BUNDLE = ResourceBundle.getBundle(prop_file);

	@Override
	public int saveMailInfo(MailSendInfo mailSendInfo){
		//�ʼ����ݸ�ʽ��֤
		return mailSendInfoMapper.insert(mailSendInfo);
	}
	@Override
	public int add4ReviewWarn(){
		int userCnt = userService.get4ReviewUsersCnt();
		int themeCnt = themeClassService.get4ReviewThemesCnt();
		int articleCnt = articleService.get4ReviewArticlesCnt();
		MailSendInfo mailInfo = new MailSendInfo();
		String subject = getForamtValue("send_review_info_subject",null);;
		String content = getForamtValue("send_review_info_content",new Object[]{userCnt,themeCnt,articleCnt});;
		String toAddr = getForamtValue("send_review_info_toAdddr",null);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setToAddr(toAddr);
		mailInfo.setCrtTime(new Date());
		mailInfo.setSendNum(0);
		mailInfo.setStatus("1");
		return saveMailInfo(mailInfo);
	}
	/**
	 * �����ʼ�����״̬��Ϣ
	 */
	public int updateMailInfo(MailSendInfo mailSendInfo){
		return mailSendInfoMapper.updateByPrimaryKey(mailSendInfo);
	}
	
	/**
	 * ��ȡ�������ʼ�
	 */
	@Override
	public List<MailSendInfo> getMails4Send(){
		return mailSendInfoMapper.selectMails4Send();
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
