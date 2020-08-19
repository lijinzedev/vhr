package com.li.vhr.mapper;

import com.li.vhr.model.Joblevel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Joblevel record);

    int insertSelective(Joblevel record);

    Joblevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Joblevel record);

    int updateByPrimaryKey(Joblevel record);

    List<Joblevel> getAllJobLevels();

    int deleteJoblevelByIds(@Param("ids") Integer[] ids);
}