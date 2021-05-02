package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Author: 落亦-
* @Date: 2021/5/2 17:15
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "company")
public class Company implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 单位名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 营业执照   图片路径
     */
    @TableField(value = "license")
    private String license;

    /**
     * 税务登记证号
     */
    @TableField(value = "tax_number")
    private String taxNumber;

    /**
     * 公司简介
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 公司地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 联系人
     */
    @TableField(value = "contact")
    private String contact;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

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