package com.learn.springsecurity.auth;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.learn.springsecurity.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Value("${jwt.signer.key}")
    private String signKey;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization)) {
            String token = authorization.substring(7);
            if (StringUtils.isNotBlank(token)) {
                try {
                    if (JWTUtil.verify(token, JWTSignerUtil.hs256(signKey.getBytes()))) {
                        JWT jwt = JWTUtil.parseToken(token);
                        JSONObject payloads = jwt.getPayloads();
                        Long userId = payloads.get(AuthConstance.USER_ID, Long.class);
                        redisTemplate.delete(AuthConstance.LOGIN_USER + userId);
                        R res = R.success("登出成功");
                        response.setContentType("application/json");
                        response.setCharacterEncoding("utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(JSONUtil.toJsonStr(res));
                        writer.close();
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
