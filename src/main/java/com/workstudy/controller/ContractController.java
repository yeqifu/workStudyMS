package com.workstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.common.shiro.ShiroUtils;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Company;
import com.workstudy.entity.Contract;
import com.workstudy.service.ContractService;
import com.workstudy.vo.ContractVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: 落亦-
 * @Date: 2021/5/6 16:26
 */
@RestController
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * 公司查询合同
     * @param contractVo
     * @return
     */
    @GetMapping("/contract")
    @RequiresRoles("company")
    public R queryContractAll(ContractVo contractVo){
        Company company = ShiroUtils.getCompanyShiro();
        Page<Contract> page = new Page<>(contractVo.getCurrentPage(),contractVo.getPageSize());
        QueryWrapper<Contract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id",company.getId());
        Page<Contract> contractPage = contractService.page(page, queryWrapper);
        return R.ok("查询成功！").put("data",contractPage);
    }

    /**
     * 公司添加合同
     * @param contract
     * @return
     */
    @PostMapping("/contract")
    @RequiresRoles("company")
    public R addContract(@RequestBody Contract contract){
        Company company = ShiroUtils.getCompanyShiro();
        contract.setCompanyId(company.getId());
        contract.setCreateTime(new Date());
        boolean save = contractService.save(contract);
        return CRUDRUtils.addR(save);
    }

    /**
     * 根据合同ID查询合同
     * @param id
     * @return
     */
    @GetMapping("/contract/{id}")
    @RequiresRoles("company")
    public R getContractById(@PathVariable("id") Integer id){
        Contract contract = contractService.getById(id);
        return R.ok("查询成功！").put("data",contract);
    }

    /**
     * 公司修改合同
     * @param contract
     * @return
     */
    @PutMapping("/contract")
    @RequiresRoles("company")
    public R updateContract(@RequestBody Contract contract){
        boolean flag = contractService.updateById(contract);
        return CRUDRUtils.updateR(flag);
    }

    /**
     * 公司删除合同
     * @param id
     * @return
     */
    @DeleteMapping("/contract/{id}")
    @RequiresRoles("company")
    public R deleteContract(@PathVariable("id") Integer id){
        boolean flag = contractService.removeById(id);
        return CRUDRUtils.deleteR(flag);
    }

}
