package com.workstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.common.shiro.ShiroUtils;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.*;
import com.workstudy.mapper.ApplyJobMapper;
import com.workstudy.service.*;
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

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private StudentApplyTeacherService studentApplyTeacherService;

    @Autowired
    private WorkReportService workReportService;

    @Autowired
    private TeacherService teacherService;

    //****************公司********************//

    /**
     * 公司查询所有学生对工作的申请----未审核
     *
     * @return
     */
    @GetMapping("/company/queryApplyJobUncheck")
    @RequiresRoles("company")
    public R queryApplyJobAllUncheckByCompany(ApplyJobVo applyJobVo) {
        Company company = ShiroUtils.getCompanyShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobAllUncheckByCompanyPage = applyJobMapper.queryApplyJobAllUncheckByCompany(page, company.getId(), 0, applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobAllUncheckByCompanyPage);
    }

    /**
     * 公司对学生申请工作进行审核
     *
     * @param id         申请工作ID
     * @param type       1-审核通过  2-审核不通过
     * @param reason     审核不通过   前端传递原因
     * @param contractId 审核通过  前端传递合同ID
     * @return
     */
    @PostMapping("/company/checkApplyJob/{id}/{type}")
    @RequiresRoles("company")
    public R checkApplyJobByCompany(@PathVariable("id") Integer id, @PathVariable("type") Integer type,
                                    @RequestParam(value = "reason", required = false) String reason, @RequestParam(value = "contractId", required = false) Integer contractId) {
        Company company = ShiroUtils.getCompanyShiro();
        ApplyJob applyJob = applyJobService.getById(id);
        applyJob.setCompanyId(company.getId());
        applyJob.setReplyTime(new Date());
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
     *
     * @param applyJobVo
     * @return
     */
    @GetMapping("/company/applyJobWork")
    @RequiresRoles("company")
    public R queryApplyJobAllWork(ApplyJobVo applyJobVo) {
        Company company = ShiroUtils.getCompanyShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobAllWorkByCompanyPage = applyJobMapper.queryApplyJobAllWork(page, company.getId(), 1, applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobAllWorkByCompanyPage);
    }

    /**
     * 公司解雇学生
     *
     * @param id     apply_job表的ID
     * @param reason 解雇原因
     * @return
     */
    @PutMapping("/company/fireApplyJob/{id}")
    @RequiresRoles("company")
    public R fireApplyJob(@PathVariable Integer id, String reason) {
        ApplyJob applyJob = applyJobService.getById(id);
        applyJob.setCompanyCheck((byte) 3);
        applyJob.setReason(reason);
        boolean flag = applyJobService.updateById(applyJob);
        if (flag) {
            return R.ok("解雇学生成功！");
        } else {
            return R.error("解雇学生失败");
        }
    }

    /**
     * 查询所有已完成工作的学生
     *
     * @return
     */
    @GetMapping("/company/applyJobFinish")
    @RequiresRoles("company")
    public R finishApplyJob(ApplyJobVo applyJobVo) {
        Company company = ShiroUtils.getCompanyShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> finishApplyJobByCompanyPage = applyJobMapper.queryFinishApplyJob(page, company.getId(), applyJobVo.getCondition());
        return R.ok("查询成功").put("data", finishApplyJobByCompanyPage);
    }

    /**
     * 学生已经完成工作  公司对学生进行评价
     *
     * @param id      apply_job的ID
     * @param comment 公司对学生的评价
     * @param star    公司对学生的打分
     * @return
     */
    @PutMapping("/company/comment/{id}")
    @RequiresRoles("company")
    public R commentForStudentApplyJob(@PathVariable("id") Integer id, @Param("comment") String comment, @Param("star") Integer star) {
        ApplyJob applyJob = applyJobService.getById(id);
        applyJob.setCompanyComment(comment);
        applyJob.setCompanyCommentStar(star);
        boolean flag = applyJobService.updateById(applyJob);
        if (flag) {
            return R.ok("评价成功");
        } else {
            return R.error("评价失败");
        }
    }

    //********************学生**************************//

    /**
     * 学生申请工作
     *
     * @param recruitId
     * @param companyId
     * @param resumeId
     * @return
     */
    @PostMapping("/applyJob/{recruitId}/{companyId}/{resumeId}")
    @RequiresRoles("student")
    public R applyJob(@PathVariable("recruitId") Integer recruitId, @PathVariable("companyId") Integer companyId, @PathVariable("resumeId") Integer resumeId) {
        Student student = ShiroUtils.getStudentShiro();
        R r = hasGuideTeacher();
        Boolean hasGuideTeacher = (Boolean) r.get("hasGuideTeacher");
        if (hasGuideTeacher) {
            Boolean aBoolean = hasApplyJob(recruitId, student);
            if (aBoolean) {
                return R.error("您已经申请了这份工作，等待公司审核中，请勿重复申请！");
            } else {
                Recruit recruit = recruitService.getById(recruitId);
                if (recruit.getStatus()==1){
                    return R.error("此工作已结束招聘，请申请其它工作！");
                }else {
                    String teacherNumber = (String) r.get("teacherNumber");
                    Teacher teacher = teacherService.queryTeacherByTeacherNumber(teacherNumber);
                    ApplyJob applyJob = new ApplyJob();
                    applyJob.setStudentId(student.getId());
                    applyJob.setRecruitId(recruitId);
                    applyJob.setCompanyId(companyId);
                    applyJob.setTeacherId(teacher.getId());
                    applyJob.setResumeId(resumeId);
                    applyJob.setCompanyCheck((byte) 0);
                    applyJob.setApplyTime(new Date());
                    boolean save = applyJobService.save(applyJob);
                    if (save) {
                        return R.ok("申请成功，请等待公司审核！");
                    } else {
                        return R.error("申请失败！");
                    }
                }
            }
        } else {
            return R.error("您还没有指导老师，请先选择指导老师！");
        }
    }

    /**
     * 查询该学生是否有指导老师  true---有指导老师   false---没有指导老师
     *
     * @return
     */
    public R hasGuideTeacher() {
        Student student = ShiroUtils.getStudentShiro();
        QueryWrapper<StudentApplyTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number", student.getStudentNumber());
        queryWrapper.eq("status", 1);
        StudentApplyTeacher studentApplyTeacher = studentApplyTeacherService.getOne(queryWrapper);
        if (null != studentApplyTeacher) {
            return R.ok().put("hasGuideTeacher", true).put("teacherNumber", studentApplyTeacher.getTeacherNumber());
        } else {
            return R.ok().put("hasGuideTeacher", false);
        }
    }

    /**
     * 查询该学生是否正在申请该工作   true---正在申请这份工作   false---没有申请这份工作
     *
     * @return
     */
    public Boolean hasApplyJob(Integer recruitId, Student student) {
        QueryWrapper<ApplyJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", student.getId());
        queryWrapper.eq("recruit_id", recruitId);
        queryWrapper.eq("company_check", 0);
        ApplyJob applyJob = applyJobService.getOne(queryWrapper);
        if (null != applyJob) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 学生查询所有公司已经通过的申请工作，进行签署合同
     *
     * @param id     申请工作ID
     * @param type   1--同意      2--拒绝
     * @param reason 拒绝原因
     * @return
     */
    @PutMapping("/signContract/{id}/{type}")
    public R signContract(@PathVariable("id") Integer id, @PathVariable("type") Integer type, @RequestParam(value = "reason", required = false) String reason) {
        Student student = ShiroUtils.getStudentShiro();
        ApplyJob applyJob = applyJobService.getById(id);
        if (type == 1) {
            applyJob.setStudentCheck((byte) 1);
            boolean flag = applyJobService.updateById(applyJob);
            if (flag) {
                return R.ok("签署合同成功，请等待指导老师审核！");
            } else {
                return R.error("签署合同失败！");
            }
        } else {
            applyJob.setStudentCheck((byte) 2);
            applyJob.setReason(reason);
            boolean flag = applyJobService.updateById(applyJob);
            if (flag) {
                return R.ok("拒绝成功！");
            } else {
                return R.error("签署合同失败！");
            }
        }
    }

    /**
     * 学生查询所有已经申请的工作----审核中和公司通过的   分页  根据公司名称模糊查询
     *
     * @param applyJobVo
     * @return
     */
    @GetMapping("/student/applyJobRejectOrPass")
    @RequiresRoles("student")
    public R queryApplyJobAllRejectOrPass(ApplyJobVo applyJobVo) {
        Student student = ShiroUtils.getStudentShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobAllRejectOrPassPage = applyJobMapper.queryApplyJobAllRejectOrPass(page, student.getId(), applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobAllRejectOrPassPage);
    }

    /**
     * 学生取消对工作的申请
     * @param id    申请工作的ID
     * @return
     */
    @DeleteMapping("/student/cancelApplyJob/{id}")
    @RequiresRoles("student")
    public R cancelApplyJob(@PathVariable("id") Integer id){
        boolean flag = applyJobService.removeById(id);
        if (flag) {
            return R.ok("取消申请成功！");
        }else {
            return R.error("取消申请失败！");
        }
    }

    /**
     * 学生查询所有正在进行中的工作    分页   根据公司名称进行模糊查询
     *
     * @param applyJobVo
     * @return
     */
    @GetMapping("/student/applyJobWork")
    @RequiresRoles("student")
    public R queryApplyJobAllWorkStudent(ApplyJobVo applyJobVo) {
        Student student = ShiroUtils.getStudentShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobAllRejectOrPassPage = applyJobMapper.queryApplyJobAllWorkStudent(page, student.getId(), applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobAllRejectOrPassPage);
    }

    /**
     * 学生离职
     *
     * @param id
     * @return
     */
    @PutMapping("/student/quit/{id}")
    @RequiresRoles("student")
    public R quitApplyJobStudent(@PathVariable("id") Integer id, String reason) {
        ApplyJob applyJob = applyJobService.getById(id);
        // 设置学生状态为3  已离职
        applyJob.setStudentCheck((byte) 3);
        applyJob.setReason(reason);
        boolean flag = applyJobService.updateById(applyJob);
        if (flag) {
            return R.ok("操作成功！");
        } else {
            return R.error("操作失败！");
        }
    }


    /**
     * 学生上传工作报告
     *
     * @param workReport
     * @return
     */
    @PostMapping("/student/uploadWorkReport")
    @RequiresRoles("student")
    public R uploadWorkReport(@RequestBody WorkReport workReport) {
        Student student = ShiroUtils.getStudentShiro();
        workReport.setStudentId(student.getId());
        workReport.setCreateTime(new Date());
        boolean save = workReportService.save(workReport);
        if (save) {
            return R.ok("上传成功！");
        } else {
            return R.error("上传失败!");
        }
    }

    /**
     * 学生修改工作报告
     * @param workReport
     * @return
     */
    @PutMapping("/student/updateWorkReport")
    @RequiresRoles("student")
    public R updateWorkReport(@RequestBody WorkReport workReport){
        boolean flag = workReportService.updateById(workReport);
        return CRUDRUtils.updateR(flag);
    }

    /**
     * 学生删除工作报告
     * @param id
     * @return
     */
    @DeleteMapping("/student/deleteWorkReport/{id}")
    @RequiresRoles("student")
    public R deleteWorkReport(@PathVariable("id") Integer id){
        boolean flag = workReportService.removeById(id);
        return CRUDRUtils.deleteR(flag);
    }

    /**
     * 学生查询所有已完成的工作
     *
     * @param applyJobVo
     * @return
     */
    @GetMapping("/student/applyJobFinish")
    @RequiresRoles("student")
    public R queryApplyJobAllFinishStudent(ApplyJobVo applyJobVo) {
        Student student = ShiroUtils.getStudentShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobAllFinishPage = applyJobMapper.queryApplyJobAllFinishStudent(page, student.getId());
        return R.ok("查询成功").put("data", applyJobAllFinishPage);
    }

    /**
     * 学生对公司进行评价
     *
     * @param id
     * @param comment
     * @param star
     * @return
     */
    @PutMapping("/student/comment/{id}")
    @RequiresRoles("student")
    public R commentAndStarApplyJob(@PathVariable("id") Integer id, @RequestParam("comment") String comment, @RequestParam("star") Integer star) {
        ApplyJob applyJob = applyJobService.getById(id);
        applyJob.setStudentComment(comment);
        applyJob.setStudentCommentStar(star);
        boolean flag = applyJobService.updateById(applyJob);
        if (flag) {
            return R.ok("评价成功");
        } else {
            return R.error("评价失败");
        }
    }


    //***************************老师*******************************//

    /**
     * 老师查询所有未审核的学生工作申请---未备案   分页  根据学生姓名name进行模糊查询
     *
     * @return
     */
    @GetMapping("/teacher/applyJobUncheck")
    @RequiresRoles("teacher")
    public R queryApplyJobUncheck(ApplyJobVo applyJobVo) {
        Teacher teacher = ShiroUtils.getTeacherShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobUncheckPage = applyJobMapper.queryApplyJobUncheck(page, teacher.getId(), applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobUncheckPage);
    }

    /**
     * 老师是否同意学生的工作申请
     *
     * @param id     工作申请ID
     * @param type   1---同意  2---拒绝
     * @param reason 原因
     * @return
     */
    @PutMapping("/teacher/agreeApplyJob/{id}/{type}")
    @RequiresRoles("teacher")
    public R teacherAgreeApplyJob(@PathVariable("id") Integer id, @PathVariable("type") Integer type, @RequestParam(value = "reason", required = false) String reason) {
        ApplyJob applyJob = applyJobService.getById(id);
        if (type == 1) {
            applyJob.setTeacherCheck((byte) 1);
            boolean flag = applyJobService.updateById(applyJob);
            if (flag) {
                return R.ok("备案成功！");
            } else {
                return R.error("备案失败！");
            }
        } else {
            applyJob.setTeacherCheck((byte) 2);
            applyJob.setReason(reason);
            boolean flag = applyJobService.updateById(applyJob);
            if (flag) {
                return R.ok("拒绝成功！");
            } else {
                return R.error("拒绝失败！");
            }
        }
    }

    /**
     * 老师查询所有正在工作中的学生   分页  根据学生姓名name进行模糊查询
     * @param applyJobVo
     * @return
     */
    @GetMapping("/teacher/applyJobStudentWork")
    public R queryAgreeApplyJobStudentWork(ApplyJobVo applyJobVo){
        Teacher teacher = ShiroUtils.getTeacherShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobStudentWorkPage = applyJobMapper.queryAgreeApplyJobStudentWork(page, teacher.getId(), applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobStudentWorkPage);
    }

    /**
     * 老师查询所有已经完成工作中的学生   分页  根据学生姓名name进行模糊查询
     * @param applyJobVo
     * @return
     */
    @GetMapping("/teacher/applyJobStudentFinishWork")
    public R queryAgreeApplyJobStudentFinishWork(ApplyJobVo applyJobVo){
        Teacher teacher = ShiroUtils.getTeacherShiro();
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobStudentWorkPage = applyJobMapper.queryAgreeApplyJobStudentFinishWork(page, teacher.getId(), applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobStudentWorkPage);
    }

    //*********************管理员***************************//

    /**
     * 查询评价信息   公司对学生   学生对公司
     * @param applyJobVo
     * @return
     */
    @GetMapping("/manager/comment")
    @RequiresRoles("manager")
    public R queryApplyJobComment(ApplyJobVo applyJobVo){
        Page<ApplyJob> page = new Page<>(applyJobVo.getCurrentPage(), applyJobVo.getPageSize());
        Page<ApplyJob> applyJobCommentPage = applyJobMapper.queryAgreeApplyJobComment(page, applyJobVo.getCondition());
        return R.ok("查询成功").put("data", applyJobCommentPage);
    }


}
