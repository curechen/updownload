package com.czy.shiyan4.service.impl;

import com.czy.shiyan4.entity.Space;
import com.czy.shiyan4.mapper.SpaceMapper;
import com.czy.shiyan4.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

    @Override
    public Space getOneByUid(int uid) {
        return spaceMapper.getOneByUid(uid);
    }

    @Override
    public void update(Space space) {
        spaceMapper.updateByPrimaryKeySelective(space);
    }

    @Override
    public void save(int uid) {
        Space space = new Space();
        space.setUid(uid);
        spaceMapper.insertSelective(space);
    }
}
