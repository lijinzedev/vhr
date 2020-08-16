package com.li.vhr.service;


import com.li.vhr.mapper.PositionMapper;
import com.li.vhr.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiJinZe
 */
@Service
public class PositionService {
    @Autowired
    PositionMapper positionMapper;

    public List<Position> getAllPositions() {

        return positionMapper.getAllPositions();
    }

    public int addPosition(Position position) {
        return positionMapper.insertSelective(position);
    }

    public int updatePosition(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    public int deletePosition(Integer position) {
        return positionMapper.deleteByPrimaryKey(position);
    }

    public int deletePositionByIds(Integer[] ids) {

        return positionMapper.deletePositionByIds(ids);
    }
}
