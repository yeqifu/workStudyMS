package com.workstudy.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 刘其悦
 */
public class StudentToken extends UsernamePasswordToken {
    public StudentToken(String userName, String password){
        super(userName,password);
    }
}
