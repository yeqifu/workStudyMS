package com.workstudy.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.common.realm.ActiveUser;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.Constant;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Student;
import com.workstudy.entity.StudentApplyTeacher;
import com.workstudy.entity.Teacher;
import com.workstudy.service.StudentApplyTeacherService;
import com.workstudy.service.StudentService;
import com.workstudy.service.TeacherService;
import com.workstudy.vo.TeacherVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:07
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentApplyTeacherService studentApplyTeacherService;

    /**
     * 学生注册
     *
     * @param student
     * @return
     */
    @PostMapping("/register/student")
    public R registerStudent(Student student) {
        // 生成32位的盐
        String salt = IdUtil.simpleUUID();
        student.setSalt(salt);
        // 将学生密码的明文加盐散列2次变成密文
        Md5Hash md5Hash = new Md5Hash(student.getPassword(),salt, Constant.HASHITERATIONS);
        student.setPassword(md5Hash.toString());
        boolean flag = studentService.save(student);
        return CRUDRUtils.addR(flag);
    }

    /**
     * 查询所有指导老师
     *
     * @return
     */
    @GetMapping("/teacher")
    @RequiresRoles("student")
    public R queryAllTeacher(TeacherVo teacherVo) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1");
        if (!StringUtils.isEmpty(teacherVo.getName())) {
            queryWrapper.like("name", teacherVo.getName());
        }
        // 创建一个分页对象
        Page<Teacher> page = new Page<>(teacherVo.getCurrentPage(), teacherVo.getPageSize());
        Page<Teacher> teacherPage = teacherService.page(page, queryWrapper);
        return R.ok("查询成功").put("teacherPage", teacherPage);
    }

    /**
     * 学生申请指导老师
     *
     * @param id 指导老师的工号
     * @return
     */
    @PostMapping("/applyTeacher/{id}")
    @RequiresRoles("student")
    public R applyTeacher(@PathVariable("id") String id) {
        String userName = SecurityUtils.getSubject().getPrincipal().toString();
        Student student = studentService.queryStudentByStudentNumber(userName);
        StudentApplyTeacher studentApplyTeacher = new StudentApplyTeacher();
        studentApplyTeacher.setStudentNumber(student.getStudentNumber());
        studentApplyTeacher.setTeacherNumber(id);
        studentApplyTeacher.setApplyDate(new Date());
        studentApplyTeacher.setStatus((byte) 0);
        boolean flag = studentApplyTeacherService.save(studentApplyTeacher);
        if (flag == true) {
            return R.ok("申请指导老师成功");
        } else {
            return R.error("申请指导老师失败");
        }
    }

    /**
     * 取消申请指导老师
     * @param id    申请ID
     * @return
     */
    @PostMapping("/cancelApplyTeacher/{id}")
    @RequiresRoles("student")
    public R cancelApplyTeacher(@PathVariable("id") Integer id){
        StudentApplyTeacher studentApplyTeacher = new StudentApplyTeacher();
        studentApplyTeacher.setId(id);
        studentApplyTeacher.setStatus((byte) 3);
        boolean flag = studentApplyTeacherService.updateById(studentApplyTeacher);
        return CRUDRUtils.updateR(flag);
    }

    /**
     * 根据学生ID查询该学生
     * @param id
     * @return
     */
    @GetMapping("/student/{id}")
    public R queryStudentById(@PathVariable("id") Integer id){
        Student student = studentService.getById(id);
        return R.ok("查询成功").put("student",student);
    }

    /**
     * 修改学生
     *
     * @param student
     * @return
     */
    @PutMapping("/student")
    @RequiresRoles("student")
    public R updateStudent(Student student) {
        boolean flag = studentService.updateById(student);
        return CRUDRUtils.updateR(flag);
    }

}
