package com.learn.springsecurity.rest;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.hutool.jwt.signers.NoneJWTSigner;
import com.learn.springsecurity.auth.AuthConstance;
import com.learn.springsecurity.auth.CustomUserDetails;
import com.learn.springsecurity.dto.LoginDto;
import com.learn.springsecurity.vo.LoginVo;
import com.learn.springsecurity.vo.R;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.KeyBuilder;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JWTSigner jwtSigner;
    @Value("${jwt.signer.key}")
    private String signKey;
    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping("/login")
    public R<?> login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        authentication = authenticationManager.authenticate(authenticationToken);

        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();


        Map<String, Object> payload = new HashMap<>();
        payload.put(AuthConstance.USER_NAME, userDetails.getUsername());
        payload.put(AuthConstance.USER_ID, String.valueOf(userDetails.getUserInfo().getId()));
        String token = JWTUtil.createToken(payload, JWTSignerUtil.hs256(signKey.getBytes()));
        redisTemplate.opsForValue().set(AuthConstance.LOGIN_USER + userDetails.getUserInfo().getId(), JSONUtil.toJsonStr(userDetails.getUserInfo()),
                3, TimeUnit.HOURS);

        R<LoginVo> data = R.data(new LoginVo(token, loginDto.getPRedirect()));
        return data;
    }
}
