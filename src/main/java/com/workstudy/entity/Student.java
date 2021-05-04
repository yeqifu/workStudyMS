package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student")
public class Student implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    @TableField(value = "student_number")
    private String studentNumber;

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
     * 专业
     */
    @TableField(value = "major")
    private String major;

    /**
     * 性别  0-男  1-女
     */
    @TableField(value = "gender")
    private Byte gender;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 学生证
     */
    @TableField(value = "student_id_card")
    private String studentIdCard;

    /**
     * 状态  0-审核中   1-已通过  2-未通过
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 盐
     */
    @TableField(value = "salt")
    private String salt;

}