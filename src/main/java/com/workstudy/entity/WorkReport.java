package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: 落亦-
* @Date: 2021/5/6 16:44
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "work_report")
public class WorkReport {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生ID
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 申请工作表ID
     */
    @TableField(value = "apply_job_id")
    private Integer applyJobId;

    /**
     * 工作报告名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 工作报告内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
}