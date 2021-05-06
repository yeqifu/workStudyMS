package com.workstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.workstudy.entity.Collection;

/**
 * @Author: 落亦-
 * @Date: 2021/5/4 15:33
 */
public interface CollectionService extends IService<Collection> {
    /**
     * 学生取消对招聘信息的收藏
     * @param studentId
     * @param recruitId
     * @return
     */
    boolean removeCollection(Integer studentId, Integer recruitId);
}
