package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.Manager;
import com.workstudy.mapper.ManagerMapper;
import com.workstudy.service.ManagerService;
import org.springframework.stereotype.Service;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 16:53
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {
    @Override
    public Manager queryManagerByUserName(String userName) {
        return baseMapper.queryManagerByUserName(userName);
    }
}
