package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.entity.Collection;
import com.workstudy.entity.Recruit;
import org.apache.ibatis.annotations.Param;

/**
* @Author: 落亦-
* @Date: 2021/5/4 15:32
*/
public interface CollectionMapper extends BaseMapper<Collection> {

    /**
     * 查询学生收藏的所有招聘信息
     * @param page
     * @param type
     * @param condition
     * @return
     */
    Page<Collection> queryCollectionAndRecruit(Page<Collection> page,@Param("type") Integer type);

    /**
     * 学生取消对招聘信息的收藏
     * @param studentId
     * @param recruitId
     * @return
     */
    boolean removeCollection(@Param("studentId") Integer studentId,@Param("recruitId") Integer recruitId);
}