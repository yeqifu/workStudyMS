package com.workstudy.common.config;

import cn.hutool.json.JSONUtil;
import com.workstudy.common.utils.Constant;
import com.workstudy.common.utils.R;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 刘其悦
 */
public class OptionsAccessControlFilter extends UserFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            setHeader(httpRequest,httpResponse);
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 为response设置header,实现跨域
     * @param request
     * @param response
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods",request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers",request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(200);
    }

    /**
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = this.getSubject(request, response);

            return subject.getPrincipal() != null;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        R r = R.error(Constant.RESPONSECODE_FAIL,"未登录");
        writer.write(JSONUtil.parseObj(r, false).toStringPretty());
        writer.flush();
        writer.close();
        return false;
    }
}
