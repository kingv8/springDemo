package com.springdemo.shiroweb.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //1 Realm 代表系统资源
/*    @Bean
    public Realm getRealm(){
        return new MyRealm();
    }*/

    //2 SecurityManager 流程控制
    @Bean
    public DefaultWebSecurityManager getSecurityManager(Realm myRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        return defaultWebSecurityManager;
    }

    //3 ShiroFilterFactoryBean 请求过滤器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager myDefaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(myDefaultWebSecurityManager);

        //配置路径过滤器
        Map<String, String> filterMap = new HashMap<>();
        //key是ant路径，支持××代表多级路径，×代表单级路径，？代表单个字符, value配置shiro的默认过滤器
        //shiro默认过滤器 DefaultFilter
        //auth, authc, perms,roles
        //表示两个资源路径需要验证
        //规则判断自上而下判断，如果上下冲突，因为优先取到上面，所以以上面为主
        //filterMap.put("/api/mobile/**", "authc,perms[mobile]");//错误补充机制：该配置方法，没有权限就会进入下面shiroFilterFactoryBean.setUnauthorizedUrl的路径
        //filterMap.put("/api/salary/**", "authc,perms[salary]");
        filterMap.put("/api/logout", "logout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/unauthorized");
        //shiroFilterFactoryBean.setFilterChainDefinitions("/api/mobile/**=authc");//另一种配置过滤的方法
        //shiroFilterFactoryBean.setLoginUrl();//设置登录页
        return shiroFilterFactoryBean;
    }

    /**
     * 不加上这个，每个controller中加上@RequiresPermissions会导致路由404
     * @RequiresPermissions这类shiro权限的注解，是DefaultAdvisorAutoProxyCreator这个bean设置之后才会生效的
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);

        return defaultAdvisorAutoProxyCreator;
    }
}
