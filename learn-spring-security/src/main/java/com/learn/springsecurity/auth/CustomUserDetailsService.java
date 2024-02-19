package com.learn.springsecurity.auth;

import com.learn.springsecurity.constance.BizConst;
import com.learn.springsecurity.mybatis.entity.UserInfo;
import com.learn.springsecurity.mybatis.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    IUserInfoService userInfoService;

    public CustomUserDetailsService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails userDetails = null;
        try {
            UserInfo userInfo = userInfoService.findUserByLoginName(username);
            if (userInfo == null) {
                throw new UsernameNotFoundException("用户" + username + "不存在!");
            }
            userDetails = new CustomUserDetails(userInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return userDetails;
    }
}
