package com.workstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.workstudy.entity.Resume;

import java.util.List;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/3 15:26
 */
public interface ResumeService extends IService<Resume> {
    /**
     * 查询所有简历
     * @param id
     * @return
     */
    List<Resume> queryResumeAll(Integer id);

}
