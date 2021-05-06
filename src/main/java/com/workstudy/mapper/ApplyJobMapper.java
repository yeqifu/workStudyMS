package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.entity.ApplyJob;
import org.apache.ibatis.annotations.Param;

/**
* @Author: 落亦-
* @Date: 2021/5/5 17:20
*/
public interface ApplyJobMapper extends BaseMapper<ApplyJob> {
    /**
     * 公司分页查询所有学生对工作的申请----未审核
     * @param page
     * @param type 类型  公司ID  学生ID
     * @param status 状态
     * @param condition
     * @return
     */
    Page<ApplyJob> queryApplyJobAllUncheckByCompany(Page<ApplyJob> page,@Param("type") Integer type, @Param("status") Integer status,@Param("condition") String condition);

    /**
     * 查询正在该公司工作的学生
     * @param page
     * @param type      公司ID
     * @param status    状态  7-老师同意（即学生正在工作中）
     * @param condition
     * @return
     */
    Page<ApplyJob> queryApplyJobAllWork(Page<ApplyJob> page,@Param("type") Integer type,@Param("status") Integer status,@Param("condition") String condition);

    /**
     * 公司查询所有已完成工作的学生
     * @param page
     * @param type
     * @param condition
     * @return
     */
    Page<ApplyJob> queryFinishApplyJob(Page<ApplyJob> page,@Param("type") Integer type,@Param("condition") String condition);

    /**
     * 学生查询所有已经申请的工作----拒绝和公司通过的
     * @param page
     * @param type      学生ID
     * @param condition
     * @return
     */
    Page<ApplyJob> queryApplyJobAllRejectOrPass(Page<ApplyJob> page,@Param("type") Integer type,@Param("condition") String condition);

    /**
     * 学生查询所有正在进行中的工作
     * @param page
     * @param type
     * @param condition
     * @return
     */
    Page<ApplyJob> queryApplyJobAllWorkStudent(Page<ApplyJob> page,@Param("type") Integer type,@Param("condition") String condition);

    /**
     * 学生查询所有已完成的工作
     * @param page
     * @param type
     * @return
     */
    Page<ApplyJob> queryApplyJobAllFinishStudent(Page<ApplyJob> page,@Param("type") Integer type);

    /**
     * 老师查询所有未审核的学生工作申请---未备案
     * @param page
     * @param type
     * @param condition
     * @return
     */
    Page<ApplyJob> queryApplyJobUncheck(Page<ApplyJob> page,@Param("type") Integer type,@Param("condition") String condition);

    /**
     * 老师查询所有正在工作中的学生
     * @param page
     * @param type
     * @param condition
     * @return
     */
    Page<ApplyJob> queryAgreeApplyJobStudentWork(Page<ApplyJob> page,@Param("type") Integer type,@Param("condition") String condition);

    /**
     * 老师查询所有已经完成工作中的学生
     * @param page
     * @param type
     * @param condition
     * @return
     */
    Page<ApplyJob> queryAgreeApplyJobStudentFinishWork(Page<ApplyJob> page,@Param("type") Integer type,@Param("condition") String condition);
}