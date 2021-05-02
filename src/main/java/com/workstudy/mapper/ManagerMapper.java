package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workstudy.entity.Manager;

/**
* @Author: 落亦-
* @Date: 2021/5/2 16:52
*/
public interface ManagerMapper extends BaseMapper<Manager> {
    /**
     * 根据管理员名称查询管理员
     * @param userName
     * @return
     */
    Manager queryManagerByUserName(String userName);
}