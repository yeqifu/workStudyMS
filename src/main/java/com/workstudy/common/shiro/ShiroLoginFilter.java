package com.workstudy.common.shiro;

import cn.hutool.json.JSONUtil;
import com.workstudy.common.utils.R;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @author 刘其悦
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {

    /**
     * 用户未登陆直接访问服务器资源，直接返回给前端一个json字符串
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String msg = "您的token已过期或您还未登陆，请先登陆";
        String token = WebUtils.toHttp(request).getHeader("token");
        if ("null".equals(token) || "".equals(token)) {
            msg = "token为空，请携带token";
        }
        R r = R.error(401, msg);
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.parseObj(r, false).toStringPretty());
        writer.flush();
        writer.close();
        return false;
    }

}
