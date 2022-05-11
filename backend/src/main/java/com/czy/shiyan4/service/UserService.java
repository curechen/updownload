package com.czy.shiyan4.service;

import com.czy.shiyan4.entity.User;

public interface UserService {
    User getUserByUserName(String username);

    void save(User user);

    User getAutorNameById(Integer uid);
}
