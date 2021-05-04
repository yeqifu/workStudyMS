package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Author: 落亦-
* @Date: 2021/5/3 15:24
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "resume")
public class Resume implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生表ID
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 头像
     */
    @TableField(value = "photo")
    private String photo;

    /**
     * 技能
     */
    @TableField(value = "skill")
    private String skill;

    /**
     * 自我介绍
     */
    @TableField(value = "introduce")
    private String introduce;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 0-未删除  1-已删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Byte isDeleted;

    @TableField(exist = false)
    private Student student;
}