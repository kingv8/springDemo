package com.springdemo.shiroweb.util;

import com.springdemo.shiroweb.bean.UserBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TestData {
    public List<UserBean> allUsers;

    public List<UserBean> getAllUsers(){
        if(null == allUsers){
            allUsers = new ArrayList<>();
            allUsers.add(new UserBean("admin", "admin", Arrays.asList("admin"), Arrays.asList("mobile, salary")));
            allUsers.add(new UserBean("admin1", "admin1", Arrays.asList("admin1"), Arrays.asList("mobile")));
            allUsers.add(new UserBean("admin2", "admin2", Arrays.asList("admin2"), Arrays.asList("salary")));
        }
        return allUsers;
    }
}
