package com.workstudy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:10
 */
public class BaseEntity implements Serializable {

    /**
     * 逻辑删除 0-未删除 1-已删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
