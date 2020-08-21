package com.li.vhr.service;

import com.li.vhr.mapper.RoleMapper;
import com.li.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    public int addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    public int deleteRole(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
