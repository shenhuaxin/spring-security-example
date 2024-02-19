package com.example.chargeclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(Customizer.withDefaults())
                .logout();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .oauth2Client(oauth2 -> oauth2
//                        .clientRegistrationRepository(this.clientRegistrationRepository())
//                        .authorizedClientRepository(this.authorizedClientRepository())
//                        .authorizedClientService(this.authorizedClientService())
//                        .authorizationCodeGrant(codeGrant -> codeGrant
//                                .authorizationRequestRepository(this.authorizationRequestRepository())
//                                .authorizationRequestResolver(this.authorizationRequestResolver())
//                                .accessTokenResponseClient(this.accessTokenResponseClient())
//                        )
//                );
//        return http.build();
//    }
}
