package com.workstudy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.workstudy.entity.Company;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 17:16
 */
public interface CompanyService extends IService<Company> {
    /**
     * 根据公司名称查询公司
     * @param userName
     * @return
     */
    Company queryCompanyByUserName(String userName);

    /**
     * 根据公司ID查询公司信息
     * @param id
     * @return
     */
    Company queryCompanyById(Integer id);
}
