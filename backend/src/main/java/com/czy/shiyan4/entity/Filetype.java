package com.czy.shiyan4.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * fileType
 * @author 
 */
@Data
public class Filetype implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 文件类型
     */
    private String fileType;

    private static final long serialVersionUID = 1L;
}