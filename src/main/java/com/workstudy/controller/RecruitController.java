package com.workstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Recruit;
import com.workstudy.mapper.RecruitMapper;
import com.workstudy.service.RecruitService;
import com.workstudy.vo.RecruitVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/3 17:03
 */
@RestController
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private RecruitMapper recruitMapper;

    /**
     * 公司发布招聘信息
     *
     * @param recruit
     * @return
     */
    @PostMapping("/recruit")
    @RequiresRoles("company")
    public R publishRecruit(Recruit recruit) {
        recruit.setPublishTime(new Date());
        boolean save = recruitService.save(recruit);
        return CRUDRUtils.addR(save);
    }

    /**
     * 公司查询自己发布的招聘信息
     *
     * @param recruitVo
     * @return
     */
    @GetMapping("/recruit/company/{id}")
    public R queryRecruitByCompany(RecruitVo recruitVo, @PathVariable("id") Integer id) {
        QueryWrapper<Recruit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", id);
        Page<Recruit> page = new Page<>(recruitVo.getCurrentPage(), recruitVo.getPageSize());
        Page<Recruit> recruitPage = recruitService.page(page, queryWrapper);
        return R.ok("查询成功").put("recruitPage", recruitPage);
    }

    /**
     * 学生查询所有的招聘信息
     *
     * @param recruitVo
     * @return
     */
    @GetMapping("/recruit")
    @RequiresRoles("student")
    public R queryAllRecruit(RecruitVo recruitVo) {
        QueryWrapper<Recruit> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(recruitVo.getPosition())) {
            queryWrapper.like("position", recruitVo.getPosition());
        }
        if (!StringUtils.isEmpty(recruitVo.getContent())) {
            queryWrapper.like("content", recruitVo.getContent());
        }
        // 查询status为0，即招聘中的招聘信息
        queryWrapper.eq("status", 0);
        Page<Recruit> page = new Page<>(recruitVo.getCurrentPage(), recruitVo.getPageSize());
        Page<Recruit> recruitPage = recruitMapper.queryAllRecruitAndCompany(page, queryWrapper);

        return R.ok("查询成功").put("recruitPage", recruitPage);
    }

    /**
     * 根据招聘信息ID查询招聘信息
     *
     * @param id
     * @return
     */
    @GetMapping("/recruit/{id}")
    public R queryRecruitById(@PathVariable("id") Integer id) {
        Recruit recruit = recruitService.getById(id);
        return R.ok("查询成功").put("recruit", recruit);
    }

    /**
     * 公司修改招聘信息
     *
     * @param recruit
     * @return
     */
    @PutMapping("/recruit")
    @RequiresRoles("company")
    public R updateRecruit(Recruit recruit) {
        boolean flag = recruitService.updateById(recruit);
        return CRUDRUtils.updateR(flag);
    }

    /**
     * 修改招聘信息的状态
     *
     * @param id     招聘信息ID
     * @param status 招聘信息状态  0-招聘中  1-已结束
     * @return
     */
    @PutMapping("/recruit/{id}/{status}")
    @RequiresRoles("company")
    public R changeStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        Recruit recruit = new Recruit();
        recruit.setId(id);
        if (status == 0) {
            recruit.setStatus((byte) 1);
        } else if (status == 1) {
            recruit.setStatus((byte) 0);
        }
        boolean flag = recruitService.updateById(recruit);
        return CRUDRUtils.updateR(flag);
    }

}
