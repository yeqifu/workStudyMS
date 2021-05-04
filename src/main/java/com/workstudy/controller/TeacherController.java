package com.workstudy.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.Constant;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Student;
import com.workstudy.entity.StudentApplyTeacher;
import com.workstudy.entity.Teacher;
import com.workstudy.service.StudentApplyTeacherService;
import com.workstudy.service.StudentService;
import com.workstudy.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/3 11:17
 */
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentApplyTeacherService studentApplyTeacherService;

    @Autowired
    private StudentService studentService;

    /**
     * 老师注册
     *
     * @param teacher
     * @return
     */
    @PostMapping("/register/teacher")
    public R registerTeacher(Teacher teacher) {
        // 生成32位的盐
        String salt = IdUtil.simpleUUID();
        teacher.setSalt(salt);
        // 将老师密码的明文加盐散列2次变成密文
        Md5Hash md5Hash = new Md5Hash(teacher.getPassword(),salt, Constant.HASHITERATIONS);
        teacher.setPassword(md5Hash.toString());
        boolean flag = teacherService.save(teacher);
        return CRUDRUtils.addR(flag);
    }

    /**
     * 查询所有选择该老师的申请
     * @return
     */
    @GetMapping("/apply")
    @RequiresRoles("teacher")
    public R queryCheckApplyAll(){
        String userName = SecurityUtils.getSubject().getPrincipal().toString();
        Teacher teacher = teacherService.queryTeacherByTeacherNumber(userName);
        QueryWrapper<StudentApplyTeacher> queryWrapper = new QueryWrapper<StudentApplyTeacher>();
        queryWrapper.eq("teacher_number",teacher.getTeacherNumber());
        queryWrapper.eq("status",0);
        List<StudentApplyTeacher> applyTeacherList = studentApplyTeacherService.list(queryWrapper);
        List<Object> list = new ArrayList<>(16);
        for (StudentApplyTeacher studentApplyTeacher : applyTeacherList) {
            String studentNumber = studentApplyTeacher.getStudentNumber();
            Student student = studentService.queryStudentByStudentNumber(studentNumber);
            Map<Object,Object> map = new HashMap<>(16);
            map.put("student",student);
            map.put("applyTime",studentApplyTeacher.getApplyDate());
            list.add(map);
        }
        return R.ok("查询成功").put("applyTeacherList",applyTeacherList);
    }

    /**
     * 老师审核学生申请
     * @param id    申请ID
     * @param type  类型 1-通过 2-拒绝
     * @param reason    拒绝填写原因
     * @return
     */
    @PostMapping("/checkApply/{id}/{type}")
    @RequiresRoles("teacher")
    public R checkApply(@PathVariable("id") Integer id,@PathVariable("type") Integer type,@RequestParam(value = "reason",required = false) String reason){
        StudentApplyTeacher studentApplyTeacher = new StudentApplyTeacher();
        studentApplyTeacher.setId(id);
        if (type == 0){
            studentApplyTeacher.setStatus((byte) 1);
        }else if (type == 1){
            studentApplyTeacher.setStatus((byte) 2);
            studentApplyTeacher.setReason(reason);
        }
        studentApplyTeacher.setApplyDate(new Date());
        boolean flag = studentApplyTeacherService.updateById(studentApplyTeacher);
        if (flag == true){
            return R.ok("审核成功");
        }else {
            return R.error("审核失败");
        }
    }

    /**
     * 根据老师ID查询该老师
     * @param id
     * @return
     */
    @GetMapping("/teacher/{id}")
    public R queryTeacherById(@PathVariable("id") Integer id){
        Teacher teacher = teacherService.getById(id);
        return R.ok("查询成功").put("teacher",teacher);
    }

    /**
     * 修改老师信息
     * @param teacher
     * @return
     */
    @PutMapping("/teacher")
    public R updateTeacher(Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        return CRUDRUtils.updateR(flag);
    }
}
