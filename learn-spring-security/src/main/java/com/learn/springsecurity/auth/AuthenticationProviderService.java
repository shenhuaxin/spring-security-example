package com.learn.springsecurity.auth;

import com.learn.springsecurity.mybatis.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
public class AuthenticationProviderService implements AuthenticationProvider {

    CustomUserDetailsService userDetailsService;
    PasswordEncoder passwordEncoder;

    public AuthenticationProviderService(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        String password = authentication.getCredentials().toString();

        CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(username);

        boolean matches = passwordEncoder.matches(password, customUserDetails.getPassword());
        if (matches) {
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        }
        throw new BadCredentialsException("账号或者密码错误");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
