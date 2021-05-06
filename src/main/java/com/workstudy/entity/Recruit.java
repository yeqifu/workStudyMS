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
* @Date: 2021/5/3 16:57
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recruit")
public class Recruit {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司ID
     */
    @TableField(value = "company_id")
    private Integer companyId;

    /**
     * 工作职位
     */
    @TableField(value = "position")
    private String position;

    /**
     * 工作地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 工作内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 工作时间
     */
    @TableField(value = "work_time")
    private String workTime;

    /**
     * 薪水
     */
    @TableField(value = "salary")
    private String salary;

    /**
     * 联系人
     */
    @TableField(value = "contact")
    private String contact;

    /**
     * 号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 发布时间
     */
    @TableField(value = "publish_time")
    private Date publishTime;

    /**
     * 状态  0-招聘中  1-已结束
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 公司
     */
    @TableField(exist = false)
    private Company company;

    /**
     * 是否被收藏
     */
    @TableField(exist = false)
    private Boolean collected;
}