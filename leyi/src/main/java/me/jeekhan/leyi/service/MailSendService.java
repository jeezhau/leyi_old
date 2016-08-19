package me.jeekhan.leyi.service;

import java.util.List;

import me.jeekhan.leyi.model.MailSendInfo;

public interface MailSendService {
	/**
	 * �����ʼ�������Ϣ
	 * @param mailSendInfo
	 * @return
	 */
	public int saveMailInfo(MailSendInfo mailSendInfo);
	
	/**
	 * �����ʼ�������Ϣ
	 * @param mailSendInfo
	 * @return
	 */
	public int updateMailInfo(MailSendInfo mailSendInfo);
	/**
	 * �������Ϣ�����ʼ����
	 * @return
	 */
	public int add4ReviewWarn();
	
	/**
	 * ��ȡ�������ʼ���100����
	 * @return
	 */
	List<MailSendInfo> getMails4Send();

}
