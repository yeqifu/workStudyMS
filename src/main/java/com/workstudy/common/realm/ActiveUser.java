package com.workstudy.common.realm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 刘其悦
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements Serializable {
    private Object user;
    private List<String> roles;
    private List<String> permissions;

}
