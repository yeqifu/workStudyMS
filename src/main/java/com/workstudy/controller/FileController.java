package com.workstudy.controller;

import cn.hutool.core.date.DateUtil;
import com.workstudy.common.utils.AppFileUtils;
import com.workstudy.common.utils.R;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    @ResponseBody
    public R uploadFile(MultipartFile mf) {
        System.out.println(mf+"............");
        long size = mf.getSize();
        System.out.println(size + "==================");
        String contentType = mf.getContentType();
        System.out.println(contentType);
        //1.得到文件名
        String oldName = UUID.randomUUID().toString();
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
            return R.error("上传失败");
        }
        return R.ok("上传成功").put("path",dirName + "/" + oldName);
    }

    /**
     * 图片下载
     */
    @RequestMapping("showImageByPath")
    public ResponseEntity<Object> showImageByPath(String path) {
        return AppFileUtils.createResponseEntity(path);
    }

}
