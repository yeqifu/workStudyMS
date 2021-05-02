package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workstudy.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:14
 */
public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 根据学号查询出该学生
     * @param studentNumber
     * @return
     */
    Student queryStudentByStudentNumber(String studentNumber);
}