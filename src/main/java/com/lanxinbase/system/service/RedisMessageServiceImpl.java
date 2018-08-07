package com.lanxinbase.system.service;


import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.service.resource.IRedisMessageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by alan.luo on 2017/11/9.
 *
 * redis消息发送服务接口
 *
 */
@Service
public class RedisMessageServiceImpl extends CompactService implements IRedisMessageService {

    @Resource
    private RedisTemplate redisTemplate;

    public RedisMessageServiceImpl() {
        super(RedisMessageServiceImpl.class);
    }

    /**
     * redis订阅推送消息
     * 消息接收类：RedisMessageListener.class:onMessage 处理消息
     * @param channel 消息频道&需要在RedisMessageListenerConfiguration.class:redisMessageListenerContainer 配置监听
     * @param message 消息内容
     * @throws IllegalServiceException
     */
    @Override
    public void sendMessage(String channel, Serializable message) throws IllegalServiceException {
        redisTemplate.convertAndSend(channel,message);
    }
}
