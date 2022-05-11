package com.czy.shiyan4.service.impl;

import com.czy.shiyan4.entity.User;
import com.czy.shiyan4.mapper.UserMapper;
import com.czy.shiyan4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getAutorNameById(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
