package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.WorkReport;
import com.workstudy.mapper.WorkReportMapper;
import com.workstudy.service.WorkReportService;
import org.springframework.stereotype.Service;

/**
 * @Author: 落亦-
 * @Date: 2021/5/6 17:14
 */
@Service
public class WorkReportServiceImpl extends ServiceImpl<WorkReportMapper, WorkReport> implements WorkReportService {
}
