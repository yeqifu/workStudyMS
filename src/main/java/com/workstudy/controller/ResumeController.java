package com.workstudy.controller;

import com.workstudy.common.realm.ActiveUser;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Resume;
import com.workstudy.entity.Student;
import com.workstudy.service.ResumeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/3 15:28
 */
@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    /**
     * 添加简历
     *
     * @param resume
     * @return
     */
    @PostMapping("/resume")
    @RequiresRoles("student")
    public R addResume(@RequestBody Resume resume) {
        // 通过shiro获取学生信息
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) activeUser.getUser();
        // 设置简历中学生的ID
        resume.setStudentId(student.getId());
        boolean save = resumeService.save(resume);
        return CRUDRUtils.addR(save);
    }

    /**
     * 查询所有简历
     *
     * @return
     */
    @GetMapping("/resume")
    @RequiresRoles("student")
    public R queryResumeAll() {
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        Student student = (Student) activeUser.getUser();
        List<Resume> resumeList = resumeService.queryResumeAll(student.getId());
        return R.ok("查询成功").put("data", resumeList);
    }

    /**
     * 根据简历ID查询该简历
     * @param id
     * @return
     */
    @GetMapping("/resume/{id}")
    @RequiresRoles("student")
    public R queryResumeById(@PathVariable("id") Integer id){
        Resume resume = resumeService.getById(id);
        return R.ok("查询成功").put("resume",resume);
    }

    /**
     * 修改简历
     *
     * @param resume
     * @return
     */
    @PutMapping("/resume")
    @RequiresRoles("student")
    public R updateResume(@RequestBody Resume resume) {
        boolean flag = resumeService.updateById(resume);
        return CRUDRUtils.updateR(flag);
    }

    /**
     * 删除简历
     *
     * @param id 简历ID
     * @return
     */
    @DeleteMapping("/resume/{id}")
    @RequiresRoles("student")
    public R deleteResume(@PathVariable("id") Integer id) {
        boolean flag = resumeService.removeById(id);
        return CRUDRUtils.deleteR(flag);
    }

}
