package me.jeekhan.leyi.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ThemeClass {
	private List<ThemeClass> children;
    private Integer id;
    
    private Integer classLvl;
    
    private String logicId;
    
    @NotEmpty(message="名称：不可为空！")
    @Size(max=7,message="名称：最大长度为7个字符！")
    private String name;

    @NotEmpty(message="关键词：不可为空！")
    @Size(max=255,message="关键词：最大长度为255个字符！")
    private String keywords;
    
    @NotEmpty(message="描述：不可为空！")
    @Size(max=600,message="描述：最大长度为600个字符！")
    private String descInfo;

    @Null
    private Date updateTime;
    
    @Null
    private Integer updateOpr;
    
    @Null
    private String enabled;
    
    public  ThemeClass(){}
    
    public  ThemeClass(String name){
    	this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogicId() {
        return logicId;
    }

    public void setLogicId(String logicId) {
        this.logicId = logicId;
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

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
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
    
    public Integer getClassLvl() {
		return classLvl;
	}

	public void setClassLvl(Integer classLvl) {
		this.classLvl = classLvl;
	}

	public List<ThemeClass> getChildren() {
		return children;
	}

	public void setChildren(List<ThemeClass> children) {
		this.children = children;
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