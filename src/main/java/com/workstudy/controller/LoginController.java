package com.workstudy.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.workstudy.common.realm.ActiveUser;
import com.workstudy.common.shiro.CompanyToken;
import com.workstudy.common.shiro.ManagerToken;
import com.workstudy.common.shiro.StudentToken;
import com.workstudy.common.shiro.TeacherToken;
import com.workstudy.common.utils.MenuTreeNode;
import com.workstudy.common.utils.R;
import com.workstudy.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:05
 */
@Controller
public class LoginController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 用户登录
     *
     * @param userName         用户名
     * @param password         密码
     * @param verificationCode 验证码
     * @param key              生成验证码的随机数
     * @param type             用户类型  0-学生  1-老师  2-用人单位 3-管理员
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public R userLogin(String userName, String password, String verificationCode, String key, Integer type) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        //从redis中获取验证码
        String code = opsForValue.get(key);
        if (null == code) {
            return R.error(500, "验证码已过期");
        }
        List<MenuTreeNode> menu = null;
        if (code.equalsIgnoreCase(verificationCode)) {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = null;
            if (type == 0) {
                usernamePasswordToken = new StudentToken(userName, password);
                List<MenuTreeNode> menuTreeNodes = new ArrayList<>();
                menuTreeNodes.add(new MenuTreeNode(1, 0, 1, "教师选择", null));
                menuTreeNodes.add(new MenuTreeNode(2, 1, 2, "申请指导老师", "/applyTeacher"));
                menuTreeNodes.add(new MenuTreeNode(3, 0, 1, "求职管理", null));
                menuTreeNodes.add(new MenuTreeNode(4, 3, 2, "个人简历", "/resume"));
                menuTreeNodes.add(new MenuTreeNode(5, 3, 2, "查看岗位", "/recruit"));
                menuTreeNodes.add(new MenuTreeNode(6, 0, 1, "岗位申请", null));
                menuTreeNodes.add(new MenuTreeNode(7, 6, 2, "岗位申请管理", "/applyRecruit"));
                menuTreeNodes.add(new MenuTreeNode(8, 0, 2, "合同备案", null));
                menuTreeNodes.add(new MenuTreeNode(9, 8, 2, "合同备案管理", "/contractRecord"));
                menuTreeNodes.add(new MenuTreeNode(10, 8, 2, "老师留言管理", "/message"));
                menu = MenuTreeNode.build(menuTreeNodes, 0);
            } else if (type == 1) {
                usernamePasswordToken = new TeacherToken(userName, password);
                List<MenuTreeNode> menuTreeNodes = new ArrayList<>();
                menuTreeNodes.add(new MenuTreeNode(1, 0, 1, "指导申请", null));
                menuTreeNodes.add(new MenuTreeNode(2, 1, 2, "审核指导申请", "/checkApply"));
                menuTreeNodes.add(new MenuTreeNode(3, 0, 1, "学生在岗", null));
                menuTreeNodes.add(new MenuTreeNode(4, 3, 2, "指导学生在岗管理", "/studentWork"));
                menuTreeNodes.add(new MenuTreeNode(5, 0, 1, "合同备案", null));
                menuTreeNodes.add(new MenuTreeNode(6, 5, 2, "合同备案管理", "/contractRecord"));
                menuTreeNodes.add(new MenuTreeNode(7, 0, 1, "留言信息", null));
                menuTreeNodes.add(new MenuTreeNode(8, 7, 2, "学生留言信息管理", "/message"));
                menu = MenuTreeNode.build(menuTreeNodes, 0);
            } else if (type == 2) {
                usernamePasswordToken = new CompanyToken(userName, password);
                List<MenuTreeNode> menuTreeNodes = new ArrayList<>();
                menuTreeNodes.add(new MenuTreeNode(1, 0, 1, "招聘信息", null));
                menuTreeNodes.add(new MenuTreeNode(2, 1, 2, "招聘信息管理", "/recruit"));
                menuTreeNodes.add(new MenuTreeNode(3, 0, 1, "求职信息", null));
                menuTreeNodes.add(new MenuTreeNode(4, 3, 2, "求职信息管理", "/jobApply"));
                menuTreeNodes.add(new MenuTreeNode(5, 0, 1, "合同备案", null));
                menuTreeNodes.add(new MenuTreeNode(6, 5, 2, "合同备案管理", "/contractRecord"));
                menu = MenuTreeNode.build(menuTreeNodes, 0);
            } else if (type == 3) {
                usernamePasswordToken = new ManagerToken(userName, password);
                List<MenuTreeNode> menuTreeNodes = new ArrayList<>();
                menuTreeNodes.add(new MenuTreeNode(1, 0, 1, "注册审核管理", null));
                menuTreeNodes.add(new MenuTreeNode(2, 1, 2, "学生注册审核管理", "/studentRegister"));
                menuTreeNodes.add(new MenuTreeNode(3, 1, 2, "老师注册审核管理", "/teacherRegister"));
                menuTreeNodes.add(new MenuTreeNode(4, 3, 2, "公司注册审核管理", "/schoolRegister"));
                menuTreeNodes.add(new MenuTreeNode(5, 0, 1, "评价管理", null));
                menuTreeNodes.add(new MenuTreeNode(6, 5, 2, "统计管理", "/contractRecord"));
                menu = MenuTreeNode.build(menuTreeNodes, 0);
            }
            try {
                subject.login(usernamePasswordToken);
                String token = subject.getSession().getId().toString();
                ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
                //将token、用户信息和权限信息返回给前端
                return R.ok("登陆成功").put("token", token).put("user", activeUser.getUser()).put("roles", activeUser.getRoles()).put("menu", menu);
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return R.error(500, "用户名或密码错误或者您选择的登陆类型错误或您的注册信息还未审核完成");
            }
        } else {
            return R.error(500, "验证码错误");
        }
    }

    /**
     * 获取验证码
     *
     * @param response
     * @param key      前端传来的随机数 该随机数对应着将验证码码存储进redis的键，值为验证码的code
     */
    @GetMapping(value = "/getVerificationCode")
    public void getVerificationCode(HttpServletResponse response, String key) {
        //四个参数分别为：宽度，高度，验证码位数，干扰线段条数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 80, 4, 10);
        //获取验证码的文字内容
        String verificationCode = lineCaptcha.getCode();
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        //将验证码的文字内容设置进redis中
        opsForValue.set(key, verificationCode);
        //设置验证码2分钟过期
        opsForValue.getOperations().expire(key, 120, TimeUnit.SECONDS);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
