package com.workstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Company;
import com.workstudy.entity.Student;
import com.workstudy.entity.Teacher;
import com.workstudy.service.CompanyService;
import com.workstudy.service.StudentService;
import com.workstudy.service.TeacherService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:06
 */
@RestController
public class RegisterCheckController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CompanyService companyService;
    
    /**
     * 查询所有还未审核的学生注册信息
     * @return
     */
    @GetMapping("/unCheckStudent")
    @RequiresRoles("manager")
    public R queryAllUnCheckStudent(){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","0");
        List<Student> studentList = studentService.list(queryWrapper);
        return R.ok("查询成功").put("studentList",studentList);
    }

    /**
     * 对学生的注册信息进行审核
     * @param id    学生信息ID
     * @return
     */
    @PostMapping("/checkStudent/{id}")
    @RequiresRoles("manager")
    public R checkStudentRegister(@PathVariable Integer id){
        Student student = new Student();
        student.setId(id);
        student.setStatus((byte) 1);
        boolean flag = studentService.updateById(student);
        return CRUDRUtils.updateR(flag);
    }

    /**
     * 查询所有还未审核的老师注册信息
     * @return
     */
    @GetMapping("/unCheckTeacher")
    @RequiresRoles("manager")
    public R queryAllUnCheckTeacher(){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","0");
        List<Teacher> teacherList = teacherService.list(queryWrapper);
        return R.ok("查询成功").put("teacherList",teacherList);
    }

    /**
     * 对老师的注册信息进行审核
     * @param id    老师信息ID
     * @return
     */
    @PostMapping("/checkTeacher/{id}")
    @RequiresRoles("manager")
    public R checkTeacherRegister(@PathVariable Integer id){
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setStatus((byte) 1);
        boolean flag = teacherService.updateById(teacher);
        return CRUDRUtils.updateR(flag);
    }

    /**
     * 查询所有还未审核的公司注册信息
     * @return
     */
    @GetMapping("/unCheckCompany")
    @RequiresRoles("manager")
    public R queryAllUnCheckCompany(){
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","0");
        List<Company> companyList = companyService.list(queryWrapper);
        return R.ok("查询成功").put("companyList",companyList);
    }

    /**
     * 对公司的注册信息进行审核
     * @param id    公司信息ID
     * @return
     */
    @PostMapping("/checkCompany/{id}")
    @RequiresRoles("manager")
    public R checkCompanyRegister(@PathVariable Integer id){
        Company company = new Company();
        company.setId(id);
        company.setStatus((byte) 1);
        boolean flag = companyService.updateById(company);
        return CRUDRUtils.updateR(flag);
    }

}
