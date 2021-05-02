package com.workstudy.common.shiro;

import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author 刘其悦
 */
@Configuration
public class TokenWebSessionManager extends DefaultWebSessionManager {

    private static final String TOKEN_HEADER="token";

    /**
     * 重写getSessionId方法
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        //从请求头里面得到token  如果不存在就生成一个
        String header = WebUtils.toHttp(request).getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(header)){
            return header;
        }
        return UUID.randomUUID().toString();
    }

}
