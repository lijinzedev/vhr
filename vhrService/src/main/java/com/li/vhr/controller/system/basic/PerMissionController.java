package com.li.vhr.controller.system.basic;

import com.li.vhr.model.Menu;
import com.li.vhr.model.Role;
import com.li.vhr.service.MenuService;
import com.li.vhr.service.PremissService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LiJinZe
 */
@RestController
@RequestMapping("/system/basic/premiss")
public class PerMissionController {
    @Autowired
    private PremissService premissService;
    @Autowired
    private MenuService menuService;
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return premissService.getAllRolse();
    }

    @GetMapping("/menus")
    List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

}
