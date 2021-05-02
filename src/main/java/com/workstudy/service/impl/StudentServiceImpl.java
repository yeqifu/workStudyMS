package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.Student;
import com.workstudy.mapper.StudentMapper;
import com.workstudy.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 15:16
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student queryStudentByStudentNumber(String studentNumber) {
        return baseMapper.queryStudentByStudentNumber(studentNumber);
    }
}
