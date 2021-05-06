package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: 落亦-
* @Date: 2021/5/3 10:28
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student_apply_teacher")
public class StudentApplyTeacher implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生学号
     */
    @TableField(value = "student_number")
    private String studentNumber;

    /**
     * 老师工号
     */
    @TableField(value = "teacher_number")
    private String teacherNumber;

    /**
     * 状态  0-审核中  1-已通过  2-未通过
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 原因
     */
    @TableField(value = "reason")
    private String reason;

    /**
     * 申请时间
     */
    @TableField(value = "apply_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    /**
     * 回复时间
     */
    @TableField(value = "reply_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyDate;

    @TableField(exist = false)
    private Teacher teacher;

    @TableField(exist = false)
    private Student student;
}