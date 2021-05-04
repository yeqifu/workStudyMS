package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.entity.StudentApplyTeacher;
import org.apache.ibatis.annotations.Param;

/**
* @Author: 落亦-
* @Date: 2021/5/3 10:28
*/
public interface StudentApplyTeacherMapper extends BaseMapper<StudentApplyTeacher> {

    /**
     * 分页查询学生的所有申请指导老师
     * @param page
     * @param studentNumber
     * @return
     */
    Page<StudentApplyTeacher> queryMyApplyAll(Page<StudentApplyTeacher> page,@Param("studentNumber") String studentNumber);

}