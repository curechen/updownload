package com.czy.shiyan4.service;

import com.czy.shiyan4.entity.Space;

public interface SpaceService {
    Space getOneByUid(int uid);

    void update(Space space);

    void save(int uid);
}
