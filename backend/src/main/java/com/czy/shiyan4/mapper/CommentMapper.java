package com.czy.shiyan4.mapper;

import com.czy.shiyan4.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface CommentMapper {
    /**
     * 指定文件id，添加一条Comment
     * @param comment 对象
     * @return  影响行数
     */
    @Insert("insert into comment(user_id,file_id,content,create_time) values(#{uid},#{fid},#{content},#{createTime})")
    int addComment(Comment comment);

    /**
     * 根据文件id，查询所有Comment
     * @return  Comment列表
     */
    @Select("select * from comment where file_id=#{fid}")
    @Results(id = "commentFileUserMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "content",property = "content"),
            @Result(column = "create_time",property = "createTime"),
            @Result(property = "user",column = "user_id",many = @Many(select = "com.czy.shiyan4.mapper.UserMapper.getUserById",fetchType = FetchType.DEFAULT))
    })
    List<Comment> getAllCommentById(@Param("fid") int id);
}
