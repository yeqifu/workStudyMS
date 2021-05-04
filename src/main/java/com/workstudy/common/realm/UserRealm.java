package com.workstudy.common.realm;

import com.workstudy.common.UnCheckException;
import com.workstudy.common.shiro.CompanyToken;
import com.workstudy.common.shiro.ManagerToken;
import com.workstudy.common.shiro.StudentToken;
import com.workstudy.common.shiro.TeacherToken;
import com.workstudy.entity.Company;
import com.workstudy.entity.Manager;
import com.workstudy.entity.Student;
import com.workstudy.entity.Teacher;
import com.workstudy.service.CompanyService;
import com.workstudy.service.ManagerService;
import com.workstudy.service.StudentService;
import com.workstudy.service.TeacherService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刘其悦
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private StudentService studentService;

    @Autowired
    @Lazy
    private TeacherService teacherService;

    @Autowired
    @Lazy
    private CompanyService companyService;

    @Autowired
    @Lazy
    private ManagerService managerService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 进行认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof StudentToken) {
            //获取学生的学号
            String studentNumber = authenticationToken.getPrincipal().toString();
            //根据学号去student表中查询出此学生
            Student student = studentService.queryStudentByStudentNumber(studentNumber);
            if (null != student) {
                ActiveUser activeUser = new ActiveUser();
                activeUser.setUser(student);

                //根据用户ID查询出该用户的类型
                List<String> roleList = new LinkedList<>();
                roleList.add("student");
                activeUser.setRoles(roleList);
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, student.getPassword(), ByteSource.Util.bytes(student.getSalt()), getName());
                return info;
            }else {
                throw new UnCheckException("您选择的登陆类型错误或您的注册信息还未审核完成");
            }
        } else if (authenticationToken instanceof TeacherToken) {
            //获取老师的工号
            String teacherNumber = authenticationToken.getPrincipal().toString();
            //根据老师工号去teacher表中查询出这个老师
            Teacher teacher = teacherService.queryTeacherByTeacherNumber(teacherNumber);
            if (null != teacher) {
                ActiveUser activeUser = new ActiveUser();
                activeUser.setUser(teacher);
                //根据用户ID查询出该用户的类型
                List<String> roleList = new LinkedList<>();
                roleList.add("teacher");
                activeUser.setRoles(roleList);
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, teacher.getPassword(), ByteSource.Util.bytes(teacher.getSalt()), getName());
                return info;
            }else {
                throw new UnCheckException("您选择的登陆类型错误或您的注册信息还未审核完成");
            }
        } else if (authenticationToken instanceof CompanyToken) {
            //获取用户名
            String userName = authenticationToken.getPrincipal().toString();
            //根据用户名去company表中查询出此用户
            Company company = companyService.queryCompanyByUserName(userName);
            if (null != company) {
                ActiveUser activeUser = new ActiveUser();
                activeUser.setUser(company);
                //根据用户ID查询出该用户的类型
                List<String> roleList = new LinkedList<>();
                roleList.add("company");
                activeUser.setRoles(roleList);
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, company.getPassword(), ByteSource.Util.bytes(company.getSalt()), getName());
                return info;
            }else {
                throw new UnCheckException("您选择的登陆类型错误或您的注册信息还未审核完成");
            }
        } else if (authenticationToken instanceof ManagerToken) {
            //获取管理员的用户名
            String userName = authenticationToken.getPrincipal().toString();
            //根据用户名去manager表中查询出管理员
            Manager manager = managerService.queryManagerByUserName(userName);
            if (null != manager) {
                ActiveUser activeUser = new ActiveUser();
                activeUser.setUser(manager);
                //根据用户ID查询出该用户的类型
                List<String> roleList = new LinkedList<>();
                roleList.add("manager");
                activeUser.setRoles(roleList);
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, manager.getPassword(), ByteSource.Util.bytes(manager.getSalt()), getName());
                return info;
            }
        }
        return null;
    }

    /**
     * 进行授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
        List<String> roles = activeUser.getRoles();
        if (null != roles && roles.size() > 0) {
            //添加角色
            simpleAuthorizationInfo.addRoles(roles);
        }
        return simpleAuthorizationInfo;
    }

}
