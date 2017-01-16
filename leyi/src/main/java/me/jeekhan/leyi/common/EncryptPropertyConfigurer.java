package me.jeekhan.leyi.common;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyConfigurer extends PropertyPlaceholderConfigurer{
	private String[] encryptPropNames = {"jdbc.url","jdbc.username","jdbc.password"}; 

	@Override
	public String convertProperty(String propName,String propValue){
		if(this.isEncryptProp(propName)){
			String decryptValue = null;
			try {
				decryptValue = DesUtils.decryptHex(propValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return decryptValue;
		}
		return propValue;
	}
	
	private boolean isEncryptProp(String propertyName){
		for(String encryptPropName:encryptPropNames){
			if(propertyName.equals(encryptPropName)){
				return true;
			}
		}
		return false;
	}

	
}
