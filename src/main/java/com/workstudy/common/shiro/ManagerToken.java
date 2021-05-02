package com.workstudy.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 刘其悦
 */
public class ManagerToken extends UsernamePasswordToken {
    public ManagerToken(String userName, String password){
        super(userName,password);
    }
}
