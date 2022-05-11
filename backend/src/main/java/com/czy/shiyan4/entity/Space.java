package com.czy.shiyan4.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * space
 * @author 
 */
@Data
public class Space implements Serializable {
    private Integer id;

    /**
     * 空间大小
     */
    private Long spaceSize;

    /**
     * 用户外健
     */
    private Integer uid;

    private static final long serialVersionUID = 1L;
}