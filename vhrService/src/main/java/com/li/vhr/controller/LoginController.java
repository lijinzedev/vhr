package com.li.vhr.controller;

import com.li.vhr.result.ResultBean;
import com.li.vhr.result.ResultProvide;
import com.li.vhr.result.ResultStatus;
import org.springframework.stereotype.Controller;
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
    public ResultBean login() {
        return ResultBean.ok(ResultStatus.NOTLOGIN);
    }
}
