package com.learn.springsecurity.auth;

import com.learn.springsecurity.mybatis.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    IUserInfoService userInfoService;
    @Autowired
    TokenFilter tokenFilter;
    @Autowired
    AuthenticationFailEntryPoint authenticationFailEntryPoint;
    @Autowired
    AuthLogoutSuccessHandler logoutSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
    }

    @Bean
    public AuthenticationProviderService authenticationProviderService() {
        return new AuthenticationProviderService(customUserDetailsService(), passwordEncoder());
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(userInfoService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置静态文件不需要认证
//        web.ignoring().antMatchers("/static/**", "/**/*.css","/**/*.js");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.csrf().disable()
                .cors().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationFailEntryPoint)
                .and()
        ;
//        http.()
        http.authorizeHttpRequests()
                .antMatchers("/auth/login", "/oauth/**", "/web/**", "/static/**", "/**/*.css","/**/*.js").permitAll()
                .anyRequest().authenticated();
        http.logout().logoutUrl("/auth/logout").logoutSuccessHandler(logoutSuccessHandler);
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
