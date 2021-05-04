package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.Resume;
import com.workstudy.mapper.ResumeMapper;
import com.workstudy.service.ResumeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 落亦-
 * @Date: 2021/5/3 15:27
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {
    @Override
    public List<Resume> queryResumeAll(Integer id) {
        return baseMapper.queryResumeAll(id);
    }
}
