package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 落亦-
 * @Date: 2021/5/5 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "apply_job")
public class ApplyJob implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生ID
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 简历ID
     */
    @TableField(value = "resume_id")
    private Integer resumeId;

    /**
     * 公司ID
     */
    @TableField(value = "company_id")
    private Integer companyId;

    /**
     * 招聘信息ID
     */
    @TableField(value = "recruit_id")
    private Integer recruitId;

    /**
     * 老师ID
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 学生申请时间
     */
    @TableField(value = "apply_time")
    private Date applyTime;

    /**
     * 公司回复时间
     */
    @TableField(value = "reply_time")
    private Date replyTime;

    /**
     * 拒绝原因
     */
    @TableField(value = "reason")
    private String reason;

    /**
     * 0-公司审核中  1-公司同意  2-公司拒绝 3-公司解雇学生
     */
    @TableField(value = "company_check")
    private Byte companyCheck;

    /**
     * 4-学生同意  5-学生拒绝  6-学生离职
     */
    @TableField(value = "student_check")
    private Byte studentCheck;

    /**
     * 7-老师同意  8-老师拒绝
     */
    @TableField(value = "teacher_check")
    private Byte teacherCheck;

    /**
     * 学生对公司评价
     */
    @TableField(value = "student_comment")
    private String studentComment;

    /**
     * 学生对公司打分
     */
    @TableField(value = "student_comment_star")
    private Integer studentCommentStar;

    /**
     * 公司对学生评价
     */
    @TableField(value = "company_comment")
    private String companyComment;

    /**
     * 公司对学生打分
     */
    @TableField(value = "company_comment_star")
    private Integer companyCommentStar;

    /**
     * 合同ID
     */
    @TableField(value = "contract_id")
    private Integer contractId;

    @TableField(exist = false)
    private Student student;

    @TableField(exist = false)
    private Company company;

    @TableField(exist = false)
    private Recruit recruit;

    @TableField(exist = false)
    private Contract contract;

    @TableField(exist = false)
    private List<WorkReport> workReport;
}