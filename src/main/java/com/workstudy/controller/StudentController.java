package com.workstudy.controller;

import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Student;
import com.workstudy.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:07
 */
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 学生注册
     * @param student
     * @return
     */
    @PostMapping("/student")
    @RequiresRoles("student")
    public R registerStudent(Student student){
        boolean flag = studentService.save(student);
        return CRUDRUtils.addR(flag);
    }

}
