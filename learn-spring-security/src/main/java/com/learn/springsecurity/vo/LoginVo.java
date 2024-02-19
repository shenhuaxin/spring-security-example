package com.learn.springsecurity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginVo {
    private String token;

    private String redirectUrl;
}
