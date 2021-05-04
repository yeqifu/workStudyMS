package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.entity.Company;
import com.workstudy.entity.Recruit;
import com.workstudy.vo.RecruitVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: 刘其悦
* @Date: 2021/5/3 16:57
*/
@Mapper
public interface RecruitMapper extends BaseMapper<Recruit> {

    /**
     * 根据公司ID查询公司信息
     * @param id
     * @return
     */
    Company queryCompanyById(Integer id);

    /**
     * 查询所有招聘信息对应的公司信息
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<Recruit> queryAllRecruitAndCompany(Page<Recruit> page, @Param(Constants.WRAPPER)Wrapper<Recruit> queryWrapper);
}