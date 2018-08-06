package com.lanxinbase.system.provider.basic;

import java.io.Serializable;

/**
 * Created by alan.luo on 2017/8/4.
 */
public class DCacheData implements Serializable {

    /**
     * 缓存的对象
     */
    private Object val;

    /**
     * 过期时间
     */
    private long expired;

    public DCacheData(Object val,long expired){
        setVal(val);
        setExpired(expired);
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }

    /**
     * 判断时间是否正确
     * @return
     */
    public boolean checkExpired(){
        return System.currentTimeMillis() < getExpired();
    }
}
