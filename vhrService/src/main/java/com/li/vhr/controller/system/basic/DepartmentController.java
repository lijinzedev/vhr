package com.li.vhr.controller.system.basic;

import com.li.vhr.api.ResultWarrp;
import com.li.vhr.model.Department;
import com.li.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LiJinZe
 * @Date: 2020/8/21 20:50
 * @Description: 部门管路
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping("/")
    public Object addDep(@RequestBody Department department) {
        departmentService.addDep(department);
        if (department.getResult() == 1) {
            ResultWarrp<Department> res = new ResultWarrp<>();
            res.setMsg("更新成功");
            res.setData(department);
            return res;
        } else {
            return "添加失败";
        }
    }

    @DeleteMapping("/{id}")
    public Object deleteDep(@PathVariable Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentService.deleteDep(department);
        if (department.getResult() == -2) {
            return "有子部门删除失败";
        } else if (department.getResult() == -1) {
            return "该部门下面有员工,删除失败";
        } else if (department.getResult() == 1) {
            return "删除成功";
        } else {
            return "删除失败未知错误";
        }

    }
}
