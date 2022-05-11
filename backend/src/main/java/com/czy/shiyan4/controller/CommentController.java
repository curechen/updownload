package com.czy.shiyan4.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.czy.shiyan4.entity.Comment;
import com.czy.shiyan4.entity.Filemessage;
import com.czy.shiyan4.entity.User;
import com.czy.shiyan4.mapper.CommentMapper;

import com.czy.shiyan4.mapper.FilemessageMapper;
import com.czy.shiyan4.mapper.UserMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    FilemessageMapper filemessageMapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/getComment",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getComment(Integer fid) {
        List<Comment> commentList = commentMapper.getAllCommentById(fid);
        return JSON.toJSONStringWithDateFormat(commentList,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
    }

    @RequestMapping(value = "/addComment",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String addComment(String content, Integer fid, Integer uid, HttpSession session, Model model) {
        System.out.println("content:"+content);
        System.out.println("fid:"+fid);
        //获取当前用户信息
//        String username = (String) session.getAttribute("loginUser");
//        User user = userMapper.getUserByName(username);
        User user = userMapper.getUserById(uid);
        Filemessage filemessage = filemessageMapper.selectByPrimaryKey(fid);
        Comment comment = new Comment(null,user.getId(),fid,user,filemessage,content,new Date());
        int i = commentMapper.addComment(comment);
        System.out.println(i);
//        model.addAttribute("Filemessage",filemessage);
        return i + "";
    }

    @RequestMapping("/toComment")
    public String toComment(int id, Model model) {
        Filemessage Filemessage = filemessageMapper.selectByPrimaryKey(id);
        model.addAttribute("Filemessage",Filemessage);
        return "comment";
    }
}
