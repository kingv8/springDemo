package com.springdemo.shiroweb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    //@RequiresPermissions代替ShiroConfig中的filterMap.put("/api/salary/**", "authc,perms[salary]");配置，
    //错误补充机制：通过@RequiresPermissions配置的会抛出异常，需要定义异常处理，见MyExceptionHandler
    @RequiresPermissions("salary")
    @RequestMapping("/query")
    public String query(){
        /*Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isPermitted("salary")){
            return "salary";
        }
        return "error";*/
        return "salary";
    }
}
