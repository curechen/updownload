package com.czy.shiyan4.mapper;

import com.czy.shiyan4.entity.Filetype;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FiletypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Filetype record);

    int insertSelective(Filetype record);

    Filetype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Filetype record);

    int updateByPrimaryKey(Filetype record);

    Filetype selectByTypeName(String fileType);

    String getTypeById(@Param("id") Integer id);

    List<Filetype> getAll();
}