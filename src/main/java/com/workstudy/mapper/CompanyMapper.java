package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workstudy.entity.Company;
import org.apache.ibatis.annotations.Mapper;

/**
* @Author: 落亦-
* @Date: 2021/5/2 17:15
*/
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
    /**
     * 根据公司名称查询公司
     * @param userName
     * @return
     */
    Company queryCompanyByUserName(String userName);
}