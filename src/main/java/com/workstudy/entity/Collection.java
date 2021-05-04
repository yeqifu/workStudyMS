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
import org.springframework.format.annotation.DateTimeFormat;

/**
* @Author: 落亦-
* @Date: 2021/5/4 15:32
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "collection")
public class Collection implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生ID
     */
    @TableField(value = "student_id")
    private Integer studentId;

    /**
     * 招聘信息ID
     */
    @TableField(value = "recruit_id")
    private Integer recruitId;

    /**
     * 收藏时间
     */
    @TableField(value = "time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

    @TableField(exist = false)
    private Recruit recruit;

}