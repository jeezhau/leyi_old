package me.jeekhan.leyi.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		Enumeration<String> attrs = request.getAttributeNames();
//		System.out.println("=========== Ù–‘£∫==============");
//		while(attrs.hasMoreElements()){
//			String attr = attrs.nextElement();
//			System.out.println(attrs + ":" + request.getAttribute(attr));
//		}
//		System.out.println("===========≤Œ ˝£∫==============");
//		Map<String,String[]> params = request.getParameterMap();
//		for(Map.Entry<String, String[]> entry:params.entrySet()){
//			System.out.println(entry.getKey() + Arrays.toString(entry.getValue()));
//		}
	}

}
