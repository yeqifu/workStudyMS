package com.workstudy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.common.shiro.ShiroUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.ApplyJob;
import com.workstudy.entity.Company;
import com.workstudy.entity.Student;
import com.workstudy.mapper.ApplyJobMapper;
import com.workstudy.service.ApplyJobService;
import com.workstudy.vo.ApplyJobVo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: 落亦-
 * @Date: 2021/5/5 17:24
 */
@RestController
public class ApplyJobController {

    @Autowired
    private ApplyJobService applyJobService;

    @Autowired
    private ApplyJobMapper applyJobMapper;

    /**
     * 学生申请工作
     *
     * @param applyJob
     * @return
     */
    @PostMapping("/applyJob")
    @RequiresRoles("student")
    public R applyJob(@RequestBody ApplyJob applyJob) {
        Student student = ShiroUtils.getStudentShiro();
        applyJob.setStudentId(student.getId());
        applyJob.setCompanyCheck((byte) 0);
        applyJob.setApplyTime(new Date());
        boolean save = applyJobService.save(applyJob);
        if (save) {
            return R.ok("申请成功，请等待公司审核！");
        } else {
            return R.error("申请失败！");
        }
    }

    //****************公司********************//

    /**
     * 公司查询所有学生对工作的申请----未审核
     *
     * @return
     */
    @GetMapping("/queryApplyJobUncheck")
    @RequiresRoles("company")
    public R queryApplyJobAllUncheckByCompany(ApplyJobVo applyJobVo) {
        Company company = ShiroUtils.getCompanyShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobAllUncheckByCompanyPage = applyJobMapper.queryApplyJobAllUncheckByCompany(page, company.getId(),0, applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobAllUncheckByCompanyPage);
    }

    /**
     * 公司对学生申请工作进行审核
     *
     * @param id    申请工作ID
     * @param type 1-审核通过  2-审核不通过
     * @param reason    审核不通过   前端传递原因
     * @param contractId  审核通过  前端传递合同ID
     * @return
     */
    @PostMapping("/checkApplyJob/{id}/{type}")
    @RequiresRoles("company")
    public R checkApplyJobByCompany(@PathVariable("id") Integer id,@PathVariable("type") Integer type,
                                    @RequestParam(value = "reason", required = false) String reason,@RequestParam("contractId") Integer contractId) {
        Company company = ShiroUtils.getCompanyShiro();
        ApplyJob applyJob = applyJobService.getById(id);
        applyJob.setCompanyId(company.getId());
        applyJob.setApplyTime(new Date());
        // 审核通过设置状态为1  不通过设置状态为2  并设置不通过的原因
        if (type == 1) {
            // 设置公司同意
            applyJob.setCompanyCheck((byte) 1);
            applyJob.setContractId(contractId);
        } else if (type == 2) {
            // 设置公司不同意
            applyJob.setCompanyCheck((byte) 2);
            applyJob.setReason(reason);
        }
        boolean flag = applyJobService.updateById(applyJob);
        if (flag) {
            return R.ok("审核成功");
        } else {
            return R.error("审核失败");
        }
    }

    /**
     * 查询正在该公司工作的所有学生
     * @param applyJobVo
     * @return
     */
    @GetMapping("/applyJobWork")
    @RequiresRoles("company")
    public R queryApplyJobAllWork(ApplyJobVo applyJobVo){
        Company company = ShiroUtils.getCompanyShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobAllWorkByCompanyPage = applyJobMapper.queryApplyJobAllWork(page, company.getId(),7, applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobAllWorkByCompanyPage);
    }

    /**
     * 公司解雇学生
     * @param id    apply_job表的ID
     * @param reason    解雇原因
     * @return
     */
    @PutMapping("/fireApplyJob/{id}")
    @RequiresRoles("company")
    public R fireApplyJob(@PathVariable Integer id,String reason){
        ApplyJob applyJob = applyJobService.getById(id);
        applyJob.setCompanyCheck((byte) 3);
        applyJob.setReason(reason);
        boolean flag = applyJobService.updateById(applyJob);
        if (flag){
            return R.ok("解雇学生成功！");
        }else {
            return R.error("解雇学生失败");
        }
    }

    /**
     * 查询所有已完成工作的学生
     * @return
     */
    @GetMapping("/applyJobFinish")
    @RequiresRoles("company")
    public R finishApplyJob(ApplyJobVo applyJobVo){
        Company company = ShiroUtils.getCompanyShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> finishApplyJobByCompanyPage = applyJobMapper.queryFinishApplyJob(page, company.getId(), applyJobVo.getCondition());
        return R.ok("查询成功").put("data", finishApplyJobByCompanyPage);
    }

    /**
     * 学生已经完成工作  公司对学生进行评价
     * @param id       apply_job的ID
     * @param comment  公司对学生的评价
     * @param star     公司对学生的打分
     * @return
     */
    @PostMapping("/comment/company/{id}")
    public R commentForStudentApplyJob(@PathVariable("id") Integer id,@Param("comment") String comment,@Param("star") Integer star){
        ApplyJob applyJob = applyJobService.getById(id);
        applyJob.setCompanyComment(comment);
        applyJob.setCompanyCommentStar(star);
        boolean flag = applyJobService.updateById(applyJob);
        if (flag){
            return R.ok("评价成功");
        }else {
            return R.error("评价失败");
        }
    }

    //********************学生**************************//


    public R query(){
        return null;
    }

}
