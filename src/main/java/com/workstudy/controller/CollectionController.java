package com.workstudy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workstudy.common.realm.ActiveUser;
import com.workstudy.common.utils.CRUDRUtils;
import com.workstudy.common.utils.R;
import com.workstudy.entity.Collection;
import com.workstudy.entity.Student;
import com.workstudy.mapper.CollectionMapper;
import com.workstudy.service.CollectionService;
import com.workstudy.vo.CollectionVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: 刘其悦
 * @Date: 2021/5/4 15:36
 */
@RestController
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CollectionMapper collectionMapper;

    /**
     * 查询学生收藏的所有招聘信息
     *
     * @param collectionVo
     * @return
     */
    @GetMapping("/collect")
    @RequiresRoles("student")
    public R queryAllCollection(CollectionVo collectionVo) {
        Page<Collection> page = new Page<>(collectionVo.getCurrentPage(), collectionVo.getPageSize());
        Page collectionAndRecruitPage = collectionMapper.queryCollectionAndRecruit(page, collectionVo.getCondition());
        return R.ok("查询成功").put("data", collectionAndRecruitPage);
    }

    /**
     * 学生收藏招聘信息
     *
     * @param id 招聘信息ID
     * @return
     */
    @PostMapping("/collectRecruit/{id}")
    @RequiresRoles("student")
    public R collectRecruit(@PathVariable Integer id) {
        // 从shiro中获取学生信息
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Student student = (Student)activeUser.getUser();
        Collection collection = new Collection();
        collection.setRecruitId(id);
        // 设置简历中学生的ID
        collection.setStudentId(student.getId());
        collection.setTime(new Date());
        boolean save = collectionService.save(collection);
        if (save == true) {
            return R.ok("收藏成功");
        } else {
            return R.error("收藏失败");
        }
    }

    /**
     * 学生取消收藏招聘信息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/cancelCollect/{id}")
    @RequiresRoles("student")
    public R cancelRecruit(@PathVariable("id") Integer id) {
        boolean flag = collectionService.removeById(id);
        if (flag == true) {
            return R.ok("取消收藏成功");
        } else {
            return R.error("取消收藏失败");
        }
    }


}
