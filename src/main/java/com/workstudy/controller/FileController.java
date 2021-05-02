package com.workstudy.controller;

import cn.hutool.core.date.DateUtil;
import com.workstudy.common.utils.AppFileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 17:39
 */
@Controller
public class FileController {

    /**
     * 文件上传
     *
     * @param mf
     * @return
     */
    @RequestMapping("uploadFile")
    public Map<String, Object> uploadFile(MultipartFile mf) {
        long size = mf.getSize();
        System.out.println(size + "==================");
        String contentType = mf.getContentType();
        System.out.println(contentType);
        //1.得到文件名
        String oldName = mf.getOriginalFilename();
        //3.得到当前日期的字符串
        String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
        //4.构造文件夹
        File dirFile = new File(AppFileUtils.UPLOAD_PATH, dirName);
        //5.判断当前文件夹是否存在
        if (!dirFile.exists()) {
            //如果不存在则创建新文件夹
            dirFile.mkdirs();
        }
        //6.构造文件对象
        File file = new File(dirFile, oldName);
        //7.把mf里面的图片信息写入file
        try {
            mf.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("path", dirName + "/" + oldName);
        return map;
    }

    /**
     * 图片下载
     */
    @RequestMapping("showImageByPath")
    public ResponseEntity<Object> showImageByPath(String path) {
        return AppFileUtils.createResponseEntity(path);
    }

}
