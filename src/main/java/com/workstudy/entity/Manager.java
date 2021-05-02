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
* @Date: 2021/5/2 16:52
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "manager")
public class Manager implements Serializable {
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
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 盐
     */
    @TableField(value = "salt")
    private String salt;
}