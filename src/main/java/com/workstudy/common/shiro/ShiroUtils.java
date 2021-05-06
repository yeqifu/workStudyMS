package com.workstudy.common.shiro;

import com.workstudy.common.realm.ActiveUser;
import com.workstudy.entity.Company;
import com.workstudy.entity.Student;
import com.workstudy.entity.Teacher;
import org.apache.shiro.SecurityUtils;

/**
 * @Author: 落亦-
 * @Date: 2021/5/5 17:27
 */
public class ShiroUtils {
    /**
     * 从shiro中获取当前登陆的老师
     * @return
     */
    public static Teacher getTeacherShiro(){
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = (Teacher)activeUser.getUser();
        return teacher;
    }

    /**
     * 从shiro中获取当前登陆的学生
     * @return
     */
    public static Student getStudentShiro(){
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student)activeUser.getUser();
        return student;
    }

    /**
     * 从shiro中获取当前登陆的公司
     * @return
     */
    public static Company getCompanyShiro(){
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Company company = (Company) activeUser.getUser();
        return company;
    }
}
