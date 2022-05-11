package com.czy.shiyan4.mapper;

import com.czy.shiyan4.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * UserMapper继承基类
 */
@Mapper
public interface UserMapper extends MyBatisBaseDao<User, Integer> {
    User getUserByName(@Param("username") String username);

    /**
     * 根据id查询user
     * @param id 用户编号
     * @return  User对象
     */
    @Select("select * from user where id = #{uid}")
    @Results(value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "username",property = "username"),
            @Result(column = "password",property = "password")
    })
    User getUserById(@Param("uid") int id);
}