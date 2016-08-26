package me.jeekhan.leyi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

@ControllerAdvice
public class GolbalExceptionHandler {
	
    @ExceptionHandler(Exception.class)  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    public String processException(NativeWebRequest request, Exception e) {  
        System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出UnauthenticatedException异常时执行");  
        return "exception"; //返回一个逻辑视图名  
    } 

}


