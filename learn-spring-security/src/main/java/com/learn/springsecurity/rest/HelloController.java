package com.learn.springsecurity.rest;

import com.learn.springsecurity.vo.R;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class HelloController {

    @GetMapping("/hello")
    public R<?> hello(Model model) {

        return R.data("hello");
    }
}
