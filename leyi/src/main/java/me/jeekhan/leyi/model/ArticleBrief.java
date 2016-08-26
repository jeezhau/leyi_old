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

    @NotNull(message="标题：不可为空！")
    @Size(max=50,message="标题：最大长度为50个字符！")
    private String name;
    
    @NotNull(message="关键词：不可为空！")
    @Size(max=255,message="关键词：最大长度为255个字符！")
    private String keywords;

    @NotNull(message="简介：不可为空！")
    @Size(max=600,message="简介：最大长度为600个字符！")
    private String brief;
    
    @NotNull(message="来源：不可为空！")
    @Pattern(regexp="^[0-2]$",message="来源：值只可为【0-自创，1-转摘，2-其他】！")
    private String source;
    
    @NotNull(message="主题：不可为空！")
    private Integer themeId;
    
    @NotNull(message="类型：不可为空！")
    @Pattern(regexp="^[0-4]$",message="类型：值只可为【0-文本，1-图册，2-视频，3-语音，4-混合】！")
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
    //返回JSON字符串
    public String toString(){
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		} 
    }
}