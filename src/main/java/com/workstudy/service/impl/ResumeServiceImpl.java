package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.Resume;
import com.workstudy.mapper.ResumeMapper;
import com.workstudy.service.ResumeService;
import org.springframework.stereotype.Service;

/**
 * @Author: 落亦-
 * @Date: 2021/5/3 15:27
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {
}
