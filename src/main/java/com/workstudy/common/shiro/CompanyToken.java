package com.workstudy.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 17:18
 */
public class CompanyToken extends UsernamePasswordToken {
    public CompanyToken(String userName, String password){
        super(userName,password);
    }
}
