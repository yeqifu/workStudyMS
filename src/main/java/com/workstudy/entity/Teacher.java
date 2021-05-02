package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @Author: 刘其悦
* @Date: 2021/5/2 16:49
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "teacher")
public class Teacher implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工号
     */
    @TableField(value = "teacher_number")
    private String teacherNumber;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 学院
     */
    @TableField(value = "college")
    private String college;

    /**
     * 教授的课程
     */
    @TableField(value = "teach_course")
    private String teachCourse;

    /**
     * 性别  0-男  1-女
     */
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 教师资格证
     */
    @TableField(value = "teacher_id_card")
    private String teacherIdCard;

    /**
     * 状态  0-审核中  1-已通过  2-未通过
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 盐
     */
    @TableField(value = "salt")
    private String salt;
}