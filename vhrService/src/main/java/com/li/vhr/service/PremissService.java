package com.li.vhr.service;

import com.li.vhr.mapper.RoleMapper;
import com.li.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremissService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRolse() {

        return roleMapper.getAllRolse();
    }
}
