package com.li.vhr.controller.system.basic;

import com.li.vhr.exception.OperationException;
import com.li.vhr.model.Joblevel;
import com.li.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/system/basic/jobLevel")
@RestController
public class JobLevelController {
    @Autowired
    private JobLevelService jobLevelService;

    @GetMapping("/")
    public List<Joblevel> getAllPositions() {
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public String addJoblevel(@RequestBody Joblevel joblevel) {
        try {
            if (jobLevelService.addJoblevel(joblevel) == 1) {
                return "添加成功";
            }
        } catch (Exception e) {

        }
        throw new OperationException("添加失败");
    }

    @PutMapping("/")
    public String updateaddJoblevel(@RequestBody Joblevel joblevel) {
        if (jobLevelService.updateaddJoblevel(joblevel) == 1) {
            return "更新成功";
        } else {
            return "更新失败";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteJoblevel(@PathVariable Integer id) {
        if (jobLevelService.deleteJoblevel(id) == 1) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }


    @DeleteMapping("/")
    public String deleteJoblevelByIds(Integer[] ids) {
        if (jobLevelService.deleteJoblevelByIds(ids) == ids.length) {
            return "删除成功";
        }
        return "删除失败";
    }
}
