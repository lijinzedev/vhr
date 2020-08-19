package com.li.vhr.service;

import com.li.vhr.mapper.JobLevelMapper;
import com.li.vhr.model.Joblevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author LiJinZe
 */
@Service
public class JobLevelService {
    @Autowired
    private JobLevelMapper jobLevelMapper;

    public List<Joblevel> getAllJobLevels() {
        return jobLevelMapper.getAllJobLevels();
    }

    public int addJoblevel(Joblevel joblevel) {
        joblevel.setCreateDate(new Date());
        joblevel.setEnabled(true);
        return jobLevelMapper.insertSelective(joblevel);
    }

    public int updateaddJoblevel(Joblevel joblevel) {
        return jobLevelMapper.updateByPrimaryKeySelective(joblevel);
    }

    public int deleteJoblevel(Integer id) {
        return jobLevelMapper.deleteByPrimaryKey(id);
    }

    public int deleteJoblevelByIds(Integer[] ids) {
        return jobLevelMapper.deleteJoblevelByIds(ids);
    }
}
