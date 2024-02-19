package com.learn.springsecurity;

import com.learn.springsecurity.mybatis.entity.UserInfo;
import com.learn.springsecurity.mybatis.service.IUserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class LoginTest {

    @Autowired
    IUserInfoService userInfoService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void addUser() {
        UserInfo entity = new UserInfo();
        entity.setLoginName("shenhuaxin");
        entity.setPassword(passwordEncoder.encode("shenhuaxin"));
        entity.setName("沈华鑫");
        entity.setPhone("18860873260");
        entity.setEmail("shx_jx@163.com");


        userInfoService.save(entity);
    }
}
