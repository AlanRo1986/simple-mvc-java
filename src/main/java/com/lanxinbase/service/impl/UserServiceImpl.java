package com.lanxinbase.service.impl;

import com.lanxinbase.model.LxUser;
import com.lanxinbase.model.common.WhereModel;
import com.lanxinbase.repository.mybatis.mapper.LxUserMapper;
import com.lanxinbase.service.resource.IUserService;
import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.pojo.PagePojo;
import com.lanxinbase.system.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends CompactService implements IUserService {

    @Resource
    private LxUserMapper userMapper;

    public UserServiceImpl(){
        super(UserServiceImpl.class);
    }

    @Override
    public int insert(LxUser record) throws IllegalServiceException {
        record.setAge(110);
        userMapper.insertSelective(record);

        record.setUsername("name"+ DateUtils.getTimeInt()+"😁❤");
        return userMapper.insertSelective(record);
    }

    @Override
    public LxUser getRowById(Integer id) throws IllegalServiceException {
        return parser(userMapper.selectByPrimaryKey(id));
    }

    @Override
    public int updateById(LxUser record) throws IllegalServiceException {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteById(Integer id) throws IllegalServiceException {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PagePojo<LxUser> getAll(WhereModel model) throws IllegalServiceException {
        PagePojo<LxUser> page = new PagePojo<>(model.getPage(),model.getPageSize());
        page.setItemNum(this.countAll(model));
        List<LxUser> list = userMapper.selectAll(model);
        for (LxUser u:list){
            parser(u);
        }
        page.setList(list);
        return page;
    }

    @Override
    public long countAll(WhereModel model) throws IllegalServiceException {
        Long res = userMapper.countAll(model);
        return res == null ? 0 : res;
    }

    @Override
    public LxUser parser(LxUser obj) throws IllegalServiceException {
        return obj;
    }
}
