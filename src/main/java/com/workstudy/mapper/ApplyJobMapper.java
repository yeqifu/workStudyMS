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
     * 查询所有已完成工作的学生
     * @param page
     * @param type
     * @param condition
     * @return
     */
    Page<ApplyJob> queryFinishApplyJob(Page<ApplyJob> page,@Param("type") Integer type,@Param("condition") String condition);
}