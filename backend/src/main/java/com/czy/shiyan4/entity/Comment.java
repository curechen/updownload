package com.czy.shiyan4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer uid;
    private Integer fid;
    private User user;
    private Filemessage file;
    private String content;
    private Date createTime;
}
