package com.li.vhr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/8/6
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "未登录";
    }
}
