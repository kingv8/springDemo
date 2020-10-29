package com.springdemo.shiroweb.config;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public Object shiroHandler(){
        return "请先获取对应的资源，再进行访问";
    }
}
