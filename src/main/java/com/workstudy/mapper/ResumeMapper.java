package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workstudy.entity.Resume;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: 落亦-
* @Date: 2021/5/3 15:24
*/
public interface ResumeMapper extends BaseMapper<Resume> {
    /**
     * 查询所有简历
     * @param id
     * @return
     */
    List<Resume> queryResumeAll(@Param("studentId") Integer id);

}