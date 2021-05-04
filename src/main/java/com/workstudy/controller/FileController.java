package com.workstudy.controller;

import cn.hutool.core.date.DateUtil;
import com.workstudy.common.utils.AppFileUtils;
import com.workstudy.common.utils.R;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    @RequestMapping("/uploadFile")
    @ResponseBody
    public R uploadFile(MultipartFile mf) {
        // 原始的文件名称
        String originalFilename = mf.getOriginalFilename();
        //1.得到文件名
        String oldName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
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
        return R.ok("上传成功").put("path", dirName + "/" + oldName);
    }

    /**
     * 解决@PathVariable中有斜杠的问题
     * @param request
     * @param path
     * @return
     */
    private String getRealPath(HttpServletRequest request, @PathVariable("path") String path) {
        final String pathMVC = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, pathMVC);
        String realPath;
        if (null != arguments && !arguments.isEmpty()) {
            realPath = path + '/' + arguments;
        } else {
            realPath = path;
        }
        return realPath;
    }

    /**
     * 删除图片
     *
     * @param path 图片路径
     * @return
     */
    @DeleteMapping("/deleteUpload/{path}/**")
    @ResponseBody
    public R deletePhoto(HttpServletRequest request, @PathVariable("path") String path) {
        String realPath = getRealPath(request, path);
        File file = new File(AppFileUtils.UPLOAD_PATH + realPath);
        //判断文件存不存在
        if (!file.exists()) {
            return R.error("删除失败，该文件不存在！");
        } else {
            file.delete();
            return R.ok("删除成功");
        }
    }

    /**
     * 图片下载
     */
    @RequestMapping("/showImageByPath/{path}/**")
    public void showImageByPath(HttpServletResponse response, HttpServletRequest request, @PathVariable("path") String path) {
        String realPath = getRealPath(request, path);
        File file = new File(AppFileUtils.UPLOAD_PATH + realPath);
        try {
            InputStream inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            // 图片文件流缓存池
            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) != -1) {
                os.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
