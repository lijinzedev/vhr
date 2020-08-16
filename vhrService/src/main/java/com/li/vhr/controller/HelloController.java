package com.li.vhr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/8/6
 */
@RestController
public class HelloController {


    @GetMapping("hello")
    public Object hello() {
        List<Object> list = new ArrayList<>();
        list.add("");
        list.add(null);
        list.add("123");
        return list;
    }


    @GetMapping("/employee/basic/hello")
    public String hello1() {

        return "/employee/basic/hello ";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello2() {

        return "/employee/advanced/hello";
    }
}
