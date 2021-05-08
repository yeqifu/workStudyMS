package com.workstudy.controller;

import cn.hutool.core.util.IdUtil;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.Constant;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Company;
import com.workstudy.entity.Student;
import com.workstudy.service.CompanyService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/3 16:15
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 公司注册
     * @param company
     * @return
     */
    @PostMapping("/register/company")
    public R registerCompany(@RequestBody Company company){
        // 生成32位的盐
        String salt = IdUtil.simpleUUID();
        company.setSalt(salt);
        // 将公司密码的明文加盐散列2次变成密文
        Md5Hash md5Hash = new Md5Hash(company.getPassword(),salt, Constant.HASHITERATIONS);
        company.setPassword(md5Hash.toString());
        boolean save = companyService.save(company);
        return CRUDRUtils.addR(save);
    }

    /**
     * 根据公司ID查询该公司
     * @param id
     * @return
     */
    @GetMapping("/company/{id}")
    public R queryCompanyById(@PathVariable("id") Integer id){
        Company company = companyService.getById(id);
        return R.ok("查询成功").put("data",company);
    }

    /**
     * 公司信息修改
     * @param company
     * @return
     */
    @PutMapping("/company")
    @RequiresRoles("company")
    public R updateCompany(@RequestBody Company company){
        Company companyDataBase = companyService.getById(company.getId());
        boolean flag = companyService.updateById(company);
        Company newCompany = companyService.getById(companyDataBase.getId());
        if (flag){
            return R.ok("修改成功!").put("data",newCompany);
        }else {
            return R.error("修改失败!");
        }
    }

}
