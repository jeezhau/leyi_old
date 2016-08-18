package me.jeekhan.leyi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArticleBrief {
    private Integer id;

    @NotNull(message="����Ϊ�գ�")
    @Size(max=50,message="��󳤶�Ϊ50���ַ���")
    private String name;
    
    @NotNull(message="����Ϊ�գ�")
    @Size(max=255,message="��󳤶�Ϊ255���ַ���")
    private String keywords;

    @NotNull(message="����Ϊ�գ�")
    @Size(max=600,message="��󳤶�Ϊ600���ַ���")
    private String brief;
    
    @NotNull(message="����Ϊ�գ�")
    @Pattern(regexp="^[0-2]$",message="ֵֻ��Ϊ��0-�Դ���1-תժ��2-��������")
    private String source;
    
    @NotNull(message="����Ϊ�գ�")
    private Integer themeId;
    
    @NotNull(message="����Ϊ�գ�")
    @Pattern(regexp="^[0-4]$",message="ֵֻ��Ϊ��0-�ı���1-ͼ�ᣬ2-��Ƶ��3-������4-��ϡ���")
    private String type;
    
    @Null
    private Date updateTime;
    
    @Null
    private Integer updateOpr;
    
    @Null
    private String enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateOpr() {
        return updateOpr;
    }

    public void setUpdateOpr(Integer updateOpr) {
        this.updateOpr = updateOpr;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
    //����JSON�ַ���
    public String toString(){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		} 
    }
}