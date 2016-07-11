package me.jeekhan.leyi.dto;

import java.util.Map;

public class Operator {
	private String username;
	
	private int userId;
	
	private int level;
	
	private Map<Integer,String> themes;
	
	public boolean hasTheme(Integer themeName){
		return themes.containsKey(themeName);
	}

	//
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Map<Integer, String> getThemes() {
		return themes;
	}

	public void setThemes(Map<Integer, String> themes) {
		this.themes = themes;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	

}
