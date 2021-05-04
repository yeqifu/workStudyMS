package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.Collection;
import com.workstudy.mapper.CollectionMapper;
import com.workstudy.service.CollectionService;
import org.springframework.stereotype.Service;

/**
 * @Author: 落亦-
 * @Date: 2021/5/4 15:34
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {
}
