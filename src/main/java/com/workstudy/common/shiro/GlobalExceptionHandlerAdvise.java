package com.workstudy.common.shiro;

import com.workstudy.common.utils.R;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 刘其悦
 */
@RestControllerAdvice
public class GlobalExceptionHandlerAdvise {
    /**
     * 捕获未授权的异常：请求需要授权的资源，但是该用户并没有权限
     * @return
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    public R unAuthorized() {
        return R.error(403,"您没有权限，请与管理员联系");
    }
}
