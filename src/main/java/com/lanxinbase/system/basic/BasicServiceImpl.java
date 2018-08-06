package com.lanxinbase.system.basic;

import org.springframework.stereotype.Service;

/**
 * Created by alan.luo on 2017/9/18.
 */
@Service
public abstract class BasicServiceImpl extends CompactService{

    public BasicServiceImpl() {
        super(BasicServiceImpl.class);
    }

}
