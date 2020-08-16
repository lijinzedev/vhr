package com.li.vhr.controller;

import com.li.vhr.api.ResultWarrp;
import com.li.vhr.model.Menu;
import com.li.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LiJinZe
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public ResultWarrp getMenu() {
        ResultWarrp<List<Menu>> resultWarrp = new ResultWarrp<>();
        resultWarrp.setData(menuService.getMenu());
        return resultWarrp;
    }
}
