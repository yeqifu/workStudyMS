package com.workstudy.common.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 刘其悦
 */
@Data
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    private String hashAlgorithmName;
    private Integer hashIterations;
    private String[] anonUrls;
    private String[] authcUrls;
    private String targetBeanName;
    private Boolean targetFilterLifecycle;
}
