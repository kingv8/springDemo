package com.springdemo.shiroweb.controller;

import com.springdemo.shiroweb.bean.UserBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login(UserBean userBean){
        Map<String, String> errorMsg = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userBean.getUserName(), userBean.getUserPass());
            try {
                currentUser.login(token);
                currentUser.getSession().setAttribute("currentUser", currentUser.getPrincipal());
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
                errorMsg.put("errorMsg", "用户不存在");
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
                errorMsg.put("errorMsg", "密码不正确");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
                errorMsg.put("errorMsg", "账户已锁定");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                errorMsg.put("errorMsg", "登录失败");
            }
        }
        return errorMsg.get("errorMsg")==null?"登录成功":errorMsg.get("errorMsg");
    }

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(){
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session.getAttribute("currentUser");
    }

    /*@GetMapping("/logout")
    public void logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }*/

    @GetMapping("/unauthorized")
    public Object unauthorized(){
        return "未经授权，无法访问";
    }
}
