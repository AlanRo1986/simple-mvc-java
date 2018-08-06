package com.lanxinbase.repository.mybatis.mapper;


import com.lanxinbase.model.LxUser;
import com.lanxinbase.model.common.WhereModel;

import java.util.List;

public interface LxUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LxUser record);

    int insertSelective(LxUser record);

    LxUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LxUser record);

    int updateByPrimaryKey(LxUser record);

    List<LxUser> selectAll(WhereModel whereModel);

    Long countAll(WhereModel whereModel);
}