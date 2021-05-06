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
* @Author: 落亦-
* @Date: 2021/5/6 16:23
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "contract")
public class Contract implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 合同名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 合同内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 公司ID
     */
    @TableField(value = "company_id")
    private Integer companyId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private Company company;
}