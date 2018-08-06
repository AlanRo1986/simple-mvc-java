package com.lanxinbase.repository.mybatis.mapper;


import com.lanxinbase.model.LxUserToken;

public interface LxUserTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LxUserToken record);

    int insertSelective(LxUserToken record);

    LxUserToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LxUserToken record);

    int updateByPrimaryKey(LxUserToken record);
}