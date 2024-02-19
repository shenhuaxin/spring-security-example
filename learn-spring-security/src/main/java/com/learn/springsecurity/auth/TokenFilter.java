package com.learn.springsecurity.auth;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.learn.springsecurity.mybatis.entity.UserInfo;
import com.learn.springsecurity.mybatis.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Value("${jwt.signer.key}")
    private String signKey;
    @Autowired
    IUserInfoService userInfoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        DefaultBearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
//        String token = bearerTokenResolver.resolve(request);
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization) && authorization.length() > 7) {
            String token = authorization.substring(7);
            if (StringUtils.isNotBlank(token)) {
                try {
                    if (JWTUtil.verify(token, JWTSignerUtil.hs256(signKey.getBytes()))) {
                        JWT jwt = JWTUtil.parseToken(token);
                        String username = (String) jwt.getPayload(AuthConstance.USER_NAME);
                        JSONObject payloads = jwt.getPayloads();
                        Long userId = payloads.get(AuthConstance.USER_ID, Long.class);
                        if (userId != null) {
                            UserInfo userInfo = userInfoService.getById(userId);
                            CustomUserDetails userDetails = new CustomUserDetails(userInfo);
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                } catch (Exception e) {
                    log.error("获取用户失败" + e.getMessage(), e);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
