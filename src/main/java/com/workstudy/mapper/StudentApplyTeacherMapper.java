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

    /**
     * 查询所有审核中的申请
     * @param page
     * @param teacherNumber
     * @return
     */
    Page<StudentApplyTeacher> queryMyCheckAll(Page<StudentApplyTeacher> page,@Param("teacherNumber") String teacherNumber,@Param("status")Integer status);

    /**
     * 查询所有审核通过和未通过的申请
     * @param page
     * @param teacherNumber
     * @return
     */
    Page<StudentApplyTeacher> queryMyCheckSuccessOrFailAll(Page<StudentApplyTeacher> page,@Param("teacherNumber") String teacherNumber);
}