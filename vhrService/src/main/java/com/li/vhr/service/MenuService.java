package com.li.vhr.service;

import com.li.vhr.mapper.MenuMapper;
import com.li.vhr.mapper.MenuRoleMapper;
import com.li.vhr.model.Hr;
import com.li.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LiJinZe
 */
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;


    public List<Menu> getMenu() {
        Hr hr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuMapper.getMenu(hr.getId());

    }

    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRoleId(Integer id) {
        return menuMapper.getMidsByRoleId(id);

    }

    @Transactional
    public boolean updateMenusRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        Integer result= menuRoleMapper.insertRecord(rid,mids);
        return result==mids.length;
    }
}
