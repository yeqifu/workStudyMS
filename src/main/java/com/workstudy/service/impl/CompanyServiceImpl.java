package com.workstudy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workstudy.entity.Company;
import com.workstudy.mapper.CompanyMapper;
import com.workstudy.service.CompanyService;
import org.springframework.stereotype.Service;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 17:16
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Override
    public Company queryCompanyByUserName(String userName) {
        return baseMapper.queryCompanyByUserName(userName);
    }

    @Override
    public Company queryCompanyById(Integer id) {
        return baseMapper.selectById(id);
    }
}
