package com.workstudy.common;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/4 13:07
 */
public class UnCheckException extends AuthenticationException {
    public UnCheckException(String message){
        super(message);
    }
}
