package com.workstudy.entity;

import lombok.Data;

/**
 * @Author: 落亦-
 * @Date: 2021/5/7 19:33
 */
@Data
public class UserPassword {
    private Integer type;
    private String oldPassword;
    private String newPassword;
}
