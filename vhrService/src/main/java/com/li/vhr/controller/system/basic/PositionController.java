package com.li.vhr.controller.system.basic;


import com.li.vhr.exception.OperationException;
import com.li.vhr.model.Position;
import com.li.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LiJinZe
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public String addPositions(@RequestBody Position position) {
        try {
            if (positionService.addPosition(position) == 1) {
                return "添加成功";
            }
        } catch (Exception e) {

        }
        throw new OperationException("添加失败");
    }

    @PutMapping("/")
    public String updatePosition(@RequestBody Position position) {
        if (positionService.updatePosition(position) == 1) {
            return "更新成功";
        } else {
            return "更新失败";
        }
    }

    @DeleteMapping("/{id}")
    public String deletePosition(@PathVariable Integer id) {
        if (positionService.deletePosition(id) == 1) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }


    @DeleteMapping("/")
    public String deletePositionByIds(Integer[] ids) {
        if (positionService.deletePositionByIds(ids) == ids.length) {
            return "删除成功";
        }
        return "删除失败";
    }
}
