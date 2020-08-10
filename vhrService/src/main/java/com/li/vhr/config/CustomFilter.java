package com.li.vhr.config;


import com.li.vhr.model.Menu;
import com.li.vhr.model.Role;
import com.li.vhr.service.HrService;
import com.li.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author LiJinZe
 * <p>
 * 根据用户传递来的请求地址分析请求需要的角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    public final static String DEFAULTROLE = "ROLE_LOGIN";
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    HrService hrService;
    @Autowired
    MenuService menuService;

    /**
     * 根据用户的请求地址分析,所需要的角色,并且把角色添加进去
     *
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // 可以使用SpringCachea避免每次都去查询数据库
        List<Menu> menusWithRole = menuService.getAllMenusWithRole();
        for (Menu menu : menusWithRole) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] list = new String[roles.size()];
                roles.stream().map(Role::getName).collect(Collectors.toList()).toArray(list);
                return SecurityConfig.createList(list);
            }

        }

        // 1. 首先获取 路径所需要的角色信息
        return SecurityConfig.createList(DEFAULTROLE);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
