package com.workstudy.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: 落亦-
 * @Date: 2021/5/2 15:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseVo {
    private Integer currentPage=1;
    private Integer pageSize=10;
}
