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
     * 学生查询所有招聘信息
     * @param page
     * @param type 学生ID
     * @param condition
     * @return
     */
    Page<Recruit> queryAllRecruitAndCompany(Page<Recruit> page,@Param("type") Integer type, @Param("condition")String condition);

    /**
     * 根据招聘信息ID查询招聘信息
     * @param id    招聘信息ID
     * @return
     */
    Recruit queryRecruitById(Integer id);
}