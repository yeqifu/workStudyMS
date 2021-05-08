package com.workstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.Constant;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Company;
import com.workstudy.entity.Manager;
import com.workstudy.entity.Student;
import com.workstudy.entity.Teacher;
import com.workstudy.service.CompanyService;
import com.workstudy.service.ManagerService;
import com.workstudy.service.StudentService;
import com.workstudy.service.TeacherService;
import com.workstudy.vo.CompanyVo;
import com.workstudy.vo.StudentVo;
import com.workstudy.vo.TeacherVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: 刘其悦
 * @Date: 2021/5/2 15:06
 */
@RestController
public class RegisterCheckController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ManagerService managerService;

    /**
     * 查询所有还未审核的学生注册信息
     *
     * @return
     */
    @GetMapping("/unCheckStudent")
    @RequiresRoles("manager")
    public R queryAllUnCheckStudent(StudentVo studentVo) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(studentVo.getCondition())) {
            queryWrapper.like("name", studentVo.getCondition()).or().like("college", studentVo.getCondition());
        }
        // 查询状态为审核中的学生
        queryWrapper.eq("status", "0");
        Page<Student> page = new Page<>(studentVo.getCurrentPage(), studentVo.getPageSize());
        Page<Student> studentPage = studentService.page(page, queryWrapper);
        return R.ok("查询成功").put("data", studentPage);
    }

    /**
     * 查询所有审核通过的学生注册信息
     *
     * @return
     */
    @GetMapping("/checkStudent")
    @RequiresRoles("manager")
    public R queryAllCheckStudent(StudentVo studentVo) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(studentVo.getCondition())) {
            queryWrapper.like("name", studentVo.getCondition()).or().like("college", studentVo.getCondition());
        }
        // 查询状态为审核中的学生
        queryWrapper.eq("status", "1").orderByDesc("id");
        Page<Student> page = new Page<>(studentVo.getCurrentPage(), studentVo.getPageSize());
        Page<Student> studentPage = studentService.page(page, queryWrapper);
        return R.ok("查询成功").put("data", studentPage);
    }

    /**
     * 对学生的注册信息进行审核----通过
     *
     * @param ids 学生信息ID
     * @return
     */
    @PostMapping("/checkStudent")
    @RequiresRoles("manager")
    public R checkStudentRegister(int[] ids) {
        boolean flag = false;
        for (Integer id : ids) {
            Student student = new Student();
            student.setId(id);
            // 设置状态为已通过
            student.setStatus((byte) 1);
            flag = studentService.updateById(student);
        }
        if (flag == true) {
            return R.ok("审核成功！");
        } else {
            return R.error("审核失败！");
        }
    }

    /**
     * 对学生的注册信息进行审核----不通过
     *
     * @param id 学生信息ID
     * @return
     */
    @DeleteMapping("/unCheckStudent/{id}")
    public R unCheckStudentRegister(@PathVariable("id") Integer id) {
        boolean flag = studentService.removeById(id);
        if (flag == true) {
            return R.ok("审核不通过成功！");
        } else {
            return R.error("审核不通过失败！");
        }
    }

    /**
     * 查询所有还未审核的老师注册信息
     *
     * @return
     */
    @GetMapping("/unCheckTeacher")
    @RequiresRoles("manager")
    public R queryAllUnCheckTeacher(TeacherVo teacherVo) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherVo.getCondition())) {
            queryWrapper.like("name", teacherVo.getCondition()).or().like("college", teacherVo.getCondition());
        }
        // 查询状态为审核中的老师
        queryWrapper.eq("status", "0");
        Page<Teacher> page = new Page<>(teacherVo.getCurrentPage(), teacherVo.getPageSize());
        Page<Teacher> teacherList = teacherService.page(page, queryWrapper);
        return R.ok("查询成功").put("data", teacherList);
    }

    /**
     * 查询所有审核通过的老师注册信息
     *
     * @return
     */
    @GetMapping("/checkTeacher")
    @RequiresRoles("manager")
    public R queryAllCheckTeacher(TeacherVo teacherVo) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherVo.getCondition())) {
            queryWrapper.like("name", teacherVo.getCondition()).or().like("college", teacherVo.getCondition()).orderByDesc("id");
        }
        // 查询状态为审核通过的老师
        queryWrapper.eq("status", "1").orderByDesc("id");
        Page<Teacher> page = new Page<>(teacherVo.getCurrentPage(), teacherVo.getPageSize());
        Page<Teacher> teacherList = teacherService.page(page, queryWrapper);
        return R.ok("查询成功").put("data", teacherList);
    }

    /**
     * 对老师的注册信息进行审核----通过
     *
     * @param ids 老师信息ID
     * @return
     */
    @PostMapping("/checkTeacher")
    @RequiresRoles("manager")
    public R checkTeacherRegister(int[] ids) {
        boolean flag = false;
        for (Integer id : ids) {
            Teacher teacher = new Teacher();
            teacher.setId(id);
            teacher.setStatus((byte) 1);
            flag = teacherService.updateById(teacher);
        }
        if (flag == true) {
            return R.ok("审核成功！");
        } else {
            return R.error("审核失败！");
        }
    }

    /**
     * 对老师的注册信息进行审核----不通过
     *
     * @param id 老师信息ID
     * @return
     */
    @DeleteMapping("/unCheckTeacher/{id}")
    public R unCheckTeacherRegister(@PathVariable("id") Integer id) {
        boolean flag = teacherService.removeById(id);
        if (flag == true) {
            return R.ok("审核不通过成功！");
        } else {
            return R.error("审核不通过失败！");
        }
    }

    /**
     * 查询所有还未审核的公司注册信息
     *
     * @return
     */
    @GetMapping("/unCheckCompany")
    @RequiresRoles("manager")
    public R queryAllUnCheckCompany(CompanyVo companyVo) {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(companyVo.getCondition())) {
            queryWrapper.like("name", companyVo.getCondition()).or().like("address", companyVo.getCondition());
        }
        queryWrapper.eq("status", "0");
        Page<Company> page = new Page<>(companyVo.getCurrentPage(), companyVo.getPageSize());
        Page<Company> companyPage = companyService.page(page, queryWrapper);
        return R.ok("查询成功").put("data", companyPage);
    }

    /**
     * 查询所有审核通过的公司注册信息
     *
     * @return
     */
    @GetMapping("/checkCompany")
    @RequiresRoles("manager")
    public R queryAllCheckCompany(CompanyVo companyVo) {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(companyVo.getCondition())) {
            queryWrapper.like("name", companyVo.getCondition()).or().like("address", companyVo.getCondition()).orderByDesc("id");
        }
        queryWrapper.eq("status", "1").orderByDesc("id");
        Page<Company> page = new Page<>(companyVo.getCurrentPage(), companyVo.getPageSize());
        Page<Company> companyPage = companyService.page(page, queryWrapper);
        return R.ok("查询成功").put("data", companyPage);
    }

    /**
     * 对公司的注册信息进行审核----通过
     *
     * @param ids 公司信息ID
     * @return
     */
    @PostMapping("/checkCompany")
    @RequiresRoles("manager")
    public R checkCompanyRegister(int[] ids) {
        boolean flag = false;
        for (Integer id : ids) {
            Company company = new Company();
            company.setId(id);
            company.setStatus((byte) 1);
            flag = companyService.updateById(company);
        }
        if (flag == true) {
            return R.ok("审核通过成功！");
        } else {
            return R.error("审核通过失败！");
        }
    }

    /**
     * 对公司的注册信息进行审核----不通过
     * @param id
     * @return
     */
    @DeleteMapping("/unCheckCompany/{id}")
    public R unCheckCompanyRegister(@PathVariable("id") Integer id){
        boolean flag = companyService.removeById(id);
        if (flag == true) {
            return R.ok("审核不通过成功！");
        } else {
            return R.error("审核不通过失败！");
        }
    }

    /**
     * 修改管理员
     *
     * @param manager
     * @return
     */
    @PutMapping("/manager")
    @RequiresRoles("manager")
    public R updateManager(@RequestBody Manager manager) {
        Manager managerDataBase = managerService.getById(manager.getId());

        String newPassword = manager.getPassword();
        if (null!=newPassword){
            String newPasswordEncryption = new Md5Hash(newPassword,managerDataBase.getSalt(), Constant.HASHITERATIONS).toString();
            manager.setPassword(newPasswordEncryption);
        }
        boolean flag = managerService.updateById(manager);
        return CRUDRUtils.updateR(flag);
    }
    
}
