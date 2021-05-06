package com.workstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.workstudy.entity.Contract;
import org.apache.ibatis.annotations.Param;

/**
* @Author: 落亦-
* @Date: 2021/5/6 16:23
*/
public interface ContractMapper extends BaseMapper<Contract> {
    /**
     * 根据合同ID查询合同
     * @param id    合同ID
     * @return
     */
    Contract queryContractById(@Param("id") Integer id);
}