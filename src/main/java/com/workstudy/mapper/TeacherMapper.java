package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workstudy.entity.Teacher;

/**
* @Author: 落亦-
* @Date: 2021/5/2 16:49
*/
public interface TeacherMapper extends BaseMapper<Teacher> {
    /**
     * 根据老师工号查询出该老师
     * @param teacherNumber
     * @return
     */
    Teacher queryTeacherByTeacherNumber(String teacherNumber);
}