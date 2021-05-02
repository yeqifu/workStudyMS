package com.workstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.workstudy.entity.Teacher;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 16:49
 */
public interface TeacherService extends IService<Teacher> {
    /**
     * 根据老师工号查询该老师
     * @param teacherNumber
     * @return
     */
    Teacher queryTeacherByTeacherNumber(String teacherNumber);
}
