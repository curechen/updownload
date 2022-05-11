package com.czy.shiyan4.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * user
 * @author 
 */
@Data
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;
}