package com.workstudy.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author: 刘其悦
 * @Date: 2021/3/23 9:50
 */
public class TeacherToken extends UsernamePasswordToken {
    public TeacherToken(String userName, String password){
        super(userName,password);
    }
}
