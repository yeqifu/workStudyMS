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
import com.workstudy.mapper.StudentApplyTeacherMapper;
import com.workstudy.service.StudentApplyTeacherService;
import com.workstudy.service.StudentService;
import com.workstudy.service.TeacherService;
import com.workstudy.vo.StudentApplyTeacherVo;
import com.workstudy.vo.TeacherVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
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

    @Autowired
    private StudentApplyTeacherMapper studentApplyTeacherMapper;

    /**
     * 学生注册
     *
     * @param student
     * @return
     */
    @PostMapping("/register/student")
    public R registerStudent(@RequestBody Student student) {
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
        // 查询所有已通过的老师
        queryWrapper.eq("status", "1");
        // 根据name和college模糊查询老师
        if (!StringUtils.isEmpty(teacherVo.getCondition())) {
            queryWrapper.like("name", teacherVo.getCondition()).or().like("college",teacherVo.getCondition());
        }
        // 创建一个分页对象
        Page<Teacher> page = new Page<>(teacherVo.getCurrentPage(), teacherVo.getPageSize());
        Page<Teacher> teacherPage = teacherService.page(page, queryWrapper);
        return R.ok("查询成功").put("data", teacherPage);
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
        // 从shiro中获取学生信息
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        Student student = (Student)activeUser.getUser();
        Boolean aBoolean = queryHasApply(student);
        if (aBoolean == true ){
            return R.error("您已经提交了申请指导老师的请求，等待老师审核中，请勿重复提交申请");
        }else {
            StudentApplyTeacher studentApplyTeacher = new StudentApplyTeacher();
            // 设置学生学号
            studentApplyTeacher.setStudentNumber(student.getStudentNumber());
            // 设置老师工号
            studentApplyTeacher.setTeacherNumber(id);
            // 设置申请时间
            studentApplyTeacher.setApplyDate(new Date());
            // 申请指导老师，等待老师审核
            studentApplyTeacher.setStatus((byte) 0);
            boolean flag = studentApplyTeacherService.save(studentApplyTeacher);
            if (flag == true) {
                return R.ok("申请指导老师成功");
            } else {
                return R.error("申请指导老师失败");
            }
        }
    }

    /**
     * 查询该学生是否已经提交了指导老师的审核
     * @return  true--已经提交了指导老师的申请   false--未提交指导老师的申请
     */
    public Boolean queryHasApply(Student student){
        QueryWrapper<StudentApplyTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number",student.getStudentNumber());
        queryWrapper.eq("status",0);
        StudentApplyTeacher studentApplyTeacher = studentApplyTeacherService.getOne(queryWrapper);
        if (null!=studentApplyTeacher){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据学生学号查询该学生的指导老师
     * @return
     */
    @GetMapping("/guide")
    @RequiresRoles("student")
    public R queryTeacherByStudent(){
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        Student student = (Student)activeUser.getUser();
        QueryWrapper<StudentApplyTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number",student.getStudentNumber());
        queryWrapper.eq("status",1);
        Boolean aBoolean = queryHasApply(student);
        StudentApplyTeacher studentApplyTeacher = studentApplyTeacherService.getOne(queryWrapper);
        if (studentApplyTeacher!=null){
            Teacher teacher = teacherService.queryTeacherByTeacherNumber(studentApplyTeacher.getTeacherNumber());
            return R.ok("查询成功").put("data",teacher).put("applyId",studentApplyTeacher.getId()).put("isApplying",false);
        }else {
            return R.ok("查询成功").put("data",null).put("isApplying",aBoolean);
        }
    }

    /**
     * 查询所有我申请
     * @return
     */
    @GetMapping("/queryApply")
    @RequiresRoles("student")
    public R queryApply(StudentApplyTeacherVo studentApplyTeacherVo){
        ActiveUser activeUser = (ActiveUser)SecurityUtils.getSubject().getPrincipal();
        Student student = (Student)activeUser.getUser();
        Page<StudentApplyTeacher> page = new Page<>(studentApplyTeacherVo.getCurrentPage(),studentApplyTeacherVo.getPageSize());
        Page<StudentApplyTeacher> queryMyApplyAll = studentApplyTeacherMapper.queryMyApplyAll(page, student.getStudentNumber());
        return R.ok("查询成功").put("data",queryMyApplyAll);
    }

    /**
     * 取消申请指导老师
     * @param id    申请ID
     * @return
     */
    @PostMapping("/cancelApplyTeacher/{id}")
    @RequiresRoles("student")
    public R cancelApplyTeacher(@PathVariable("id") Integer id){
        boolean flag = studentApplyTeacherService.removeById(id);
        return CRUDRUtils.deleteR(flag);
    }

    /**
     * 根据学生ID查询该学生
     * @param id
     * @return
     */
    @GetMapping("/student/{id}")
    public R queryStudentById(@PathVariable("id") Integer id){
        Student student = studentService.getById(id);
        return R.ok("查询成功").put("data",student);
    }

    /**
     * 修改学生
     *
     * @param student
     * @return
     */
    @PutMapping("/student")
    @RequiresRoles("student")
    public R updateStudent(@RequestBody Student student) {
        Student studentDataBase = studentService.getById(student.getId());

        String newPassword = student.getPassword();
        if (null!=newPassword){
            String newPasswordEncryption = new Md5Hash(newPassword,studentDataBase.getSalt(),Constant.HASHITERATIONS).toString();
            student.setPassword(newPasswordEncryption);
        }
        boolean flag = studentService.updateById(student);
        return CRUDRUtils.updateR(flag);
    }

}
