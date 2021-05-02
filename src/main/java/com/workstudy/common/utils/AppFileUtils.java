package com.workstudy.common.utils;

import cn.hutool.core.io.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: 刘其悦
 */
public class AppFileUtils {

    /**
     * 文件上传的保存路径  默认值
     */
    public static String UPLOAD_PATH = "G:/upload/";

    static {
        //通过反射的方式，读取配置文件的存储地址
        InputStream stream = AppFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String property = properties.getProperty("filepath");
        if (null != property) {
            UPLOAD_PATH = property;
        }
    }

    /**
     * 文件下载
     *
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //1.构造文件对象
        File file = new File(UPLOAD_PATH, path);
        if (file.exists()) {
            //将下载的文件，封装byte[]
            byte[] bytes = null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //创建封装响应头信息的对象
            HttpHeaders header = new HttpHeaders();
            //封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //创建ResponseEntity对象
            ResponseEntity<Object> entity = new ResponseEntity<Object>(bytes, header, HttpStatus.CREATED);
            return entity;
        }
        return null;
    }

}
