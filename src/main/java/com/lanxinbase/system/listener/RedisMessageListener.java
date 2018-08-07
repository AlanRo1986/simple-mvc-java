package com.lanxinbase.system.listener;

import com.lanxinbase.constant.ConstantRedis;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * Created by alan.luo on 2017/11/9.
 *
 * 使用方法，目前频道有两种
 * @Autowired
 * private IRedisMessageService messageService;
 * messageService.sendMessage(ConstantRedis.CHANNEL,JsonUtil.ObjectToJson(textMessagePojo));
 *
 */

public class RedisMessageListener implements MessageListener {

    private RedisTemplate<Serializable,Serializable> redisTemplate;

    private Logger logger = Logger.getLogger(RedisMessageListener.class.getName());

    public RedisMessageListener(){
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        String content = (String) redisTemplate.getValueSerializer().deserialize(body);
        String topic = redisTemplate.getStringSerializer().deserialize(channel);

        logger.info(String.format("Redis Sub:channel=%s,content=%s",topic,content));

    }

    public void destroy(){

    }

}
