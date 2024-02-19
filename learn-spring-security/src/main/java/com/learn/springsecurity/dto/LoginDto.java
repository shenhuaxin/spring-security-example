package com.learn.springsecurity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginDto {

    private String username;

    private String password;

    @JsonProperty("p_redirect")
    private String pRedirect;
}
