package me.jeekhan.leyi.dao;

import java.util.List;

import me.jeekhan.leyi.model.MailSendInfo;

public interface MailSendInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MailSendInfo mailSendInfo);

    int updateByPrimaryKey(MailSendInfo record);
    //��ȡ100���ʼ���¼���ڷ���
    List<MailSendInfo> selectMails4Send();
    
}