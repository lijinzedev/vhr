package com.li.vhr.service;

import com.li.vhr.mapper.DepartmentMapper;
import com.li.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Curiosirt
 * @Date: 2020/8/21 20:56
 * @Description: 部门服务类
 */
@Component
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 获取所有部门
     * @return
     */
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
    }

    public void deleteDep(Department department) {
        departmentMapper.deleteDep(department);
    }
}
