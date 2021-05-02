package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.Teacher;
import com.workstudy.mapper.TeacherMapper;
import com.workstudy.service.TeacherService;
import org.springframework.stereotype.Service;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 16:50
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher queryTeacherByTeacherNumber(String teacherNumber) {
        return baseMapper.queryTeacherByTeacherNumber(teacherNumber);
    }
}
