package com.springdemo.shiroweb.service;

import com.springdemo.shiroweb.bean.UserBean;
import com.springdemo.shiroweb.util.TestData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Resource
    private TestData testData;

    public UserBean queryUserByName(String username){
        UserBean userBean = new UserBean();
        List<Object> queryUsers = testData.getAllUsers().stream().filter(user -> username.equals(user.getUserName())).collect(Collectors.toList());
        if(queryUsers.isEmpty()){
            return null;
        }else {
            BeanUtils.copyProperties(queryUsers.get(0), userBean);
            return userBean;
        }
    }
}
