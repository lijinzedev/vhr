package com.li.vhr.controller.system.basic;

import com.li.vhr.model.Menu;
import com.li.vhr.model.Role;
import com.li.vhr.service.MenuService;
import com.li.vhr.service.PremissService;
import com.li.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return premissService.getAllRolse();
    }

    @GetMapping("/menus")
    List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{rid}")
    public List<Integer> getMidsByRoleId(@PathVariable("rid") Integer id) {
        return menuService.getMidsByRoleId(id);
    }

    @PutMapping("/{rid}")
    public String updateMenusRole(@PathVariable("rid") Integer rid, @RequestBody Integer mids[]) {
        if (menuService.updateMenusRole(rid, mids)) {
            return "更新成功";
        } else {
            return "更新失败";
        }
    }

    @PostMapping("/")
    public String addRole(@RequestBody Role role) {
        if (roleService.addRole(role) == 1) {
            return "添加成功";
        }
        return "添加失败";
    }

    @DeleteMapping("/role/{rid}")
    public String deleteRoleById(@PathVariable("rid") Integer rid) {
        if (roleService.deleteRole(rid) == 1) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }
}
