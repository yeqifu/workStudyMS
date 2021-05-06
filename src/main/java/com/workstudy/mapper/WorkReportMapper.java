package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workstudy.entity.WorkReport;

import java.util.List;

/**
* @Author: 落亦-
* @Date: 2021/5/6 16:44
*/
public interface WorkReportMapper extends BaseMapper<WorkReport> {

    /**
     * 根据申请工作表查询工作报告
     * @param id
     * @return
     */
    List<WorkReport> queryWorkReportById(Integer id);

}