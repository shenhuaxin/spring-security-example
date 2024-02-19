package com.learn.springsecurity.auth;

import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.signer.key}")
    private String key;

    @Bean
    public JWTSigner jwtSigner() {
        return JWTSignerUtil.hs256(key.getBytes());
    }
}
