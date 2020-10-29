package com.springdemo.shiroweb.config;

import com.springdemo.shiroweb.bean.UserBean;
import com.springdemo.shiroweb.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 触发权限验证的时候才会触发该方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("<<<enter MyRealm doGetAuthorizationInfo method ");
        UserBean userBean = (UserBean)principalCollection.asList().get(0);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(userBean.getUserRoles());
        simpleAuthorizationInfo.addStringPermissions(userBean.getUserPerms());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info(">>>enter MyRealm doGetAuthenticationInfo method");
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        String username = userToken.getUsername();
        //获取数据库中的用户，来跟当前用户进行比较，认证
        UserBean userBean = userService.queryUserByName(username);
        if( null == userBean){
            return null;//后面会抛出UnknowAccountException
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userBean, userBean.getUserPass(), "myRealm");
        return simpleAuthenticationInfo;
    }
}
