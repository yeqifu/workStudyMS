package com.workstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.workstudy.entity.Student;
import org.springframework.stereotype.Service;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 15:08
 */
public interface StudentService extends IService<Student> {
    /**
     * 根据学号查询出该学生
     * @param studentNumber
     * @return
     */
    Student queryStudentByStudentNumber(String studentNumber);
}
