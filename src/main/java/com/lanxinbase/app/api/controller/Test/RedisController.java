package com.lanxinbase.app.api.controller.Test;

import com.lanxinbase.constant.ConstantRedis;
import com.lanxinbase.system.core.ResultResp;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.provider.CacheProvider;
import com.lanxinbase.system.service.resource.IRedisMessageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by alan on 2018/8/7.
 */
@RestController
public class RedisController {

    @Resource
    private IRedisMessageService redisMessageService;

    @Resource
    private CacheProvider cacheProvider;

    @RequestMapping(value = "/redis-send")
    public ResultResp<String> send(HttpServletRequest request) {
        ResultResp<String> resp = new ResultResp<>();

        try {
            redisMessageService.sendMessage(ConstantRedis.CHANNEL, "发送时间：" + System.currentTimeMillis());
        } catch (IllegalServiceException e) {
            e.printStackTrace();
        }

        return resp;
    }


    @RequestMapping(value = "/redis-set")
    public ResultResp<String> set(HttpServletRequest request) {
        ResultResp<String> resp = new ResultResp<>();
        cacheProvider.put("test", "缓存时间：" + System.currentTimeMillis(), 10);
        return resp;
    }

    @RequestMapping(value = "/redis-get")
    public ResultResp<String> get(HttpServletRequest request) {
        ResultResp<String> resp = new ResultResp<>();

        resp.setData((String) cacheProvider.get("test"));
        return resp;
    }

}
