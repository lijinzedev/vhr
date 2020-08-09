package com.li.vhr.controller;

import com.li.vhr.model.Hr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/8/6
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {

        return "Hello";
    }
}
