package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;

public interface ArticleBriefMapper {
	//�������״̬
    int updateEnabledStatus(@Param("id")Integer id,@Param("enabled")String enabled);
    //�²���һ�����¼�¼
    int insert(ArticleBrief record);
    
    ArticleBrief selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ArticleBrief record);
    
    List<ArticleBrief> selectArticlesByUser(@Param("userId") int userId,@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticles(@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticlesByTheme(@Param("themeId") int themeId,@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    //ȡ��������һ����¼
    ArticleBrief selectLatestRecrod(ArticleBrief record);
    //ȡ���´���˵�20����¼
    List<ArticleBrief> selectArticles4Review();
    //ͳ�ƴ������������
    int countArticles4Review();
}