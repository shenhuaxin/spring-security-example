package com.learn.springsecurity.auth;

import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.learn.springsecurity.vo.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 认证失败处理类 返回未授权
 * 
 * @author ruoyi
 */
@Component
public class AuthenticationFailEntryPoint implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Value("${auth.login.view.url}")
    private String loginView;
    @Value("${auth.base.url}")
    private String baseUrl;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        if ("/oauth/authorize".equals(request.getServletPath())) {
            String r = URLUtil.encodeAll(baseUrl + "/oauth/authorize?"+request.getQueryString());
            response.sendRedirect(loginView + "?r="+ r);
            return;
        }
        R res = R.fail(AuthConstance.UNAUTHORIZED, "用户未登录，请登录后访问");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(res));
        writer.close();
    }
}
