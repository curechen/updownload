package com.czy.shiyan4.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * fileMessage
 * @author 
 */
@Data
public class Filemessage implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 存储文件名
     */
    private String storeName;

    /**
     * 真实文件名
     */
    private String realName;

    /**
     * 上传时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 下载次数
     */
    private Integer count;

    /**
     * 类型外健
     */
    private Integer tid;

    /**
     * 用户外健
     */
    private Integer uid;

    /**
     * 文件状态 0-冻结 1-解冻
     */
    private Integer state;

    private static final long serialVersionUID = 1L;
}