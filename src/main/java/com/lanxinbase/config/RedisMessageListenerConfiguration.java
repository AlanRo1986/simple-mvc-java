package com.lanxinbase.config;

import com.lanxinbase.constant.ConstantRedis;
import com.lanxinbase.system.listener.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by alan.luo on 2017/11/9.
 */
@Configuration
public class RedisMessageListenerConfiguration {

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(ConstantRedis.maxIdle);
        jedisPoolConfig.setMaxTotal(ConstantRedis.maxTotal);
        jedisPoolConfig.setMaxWaitMillis(ConstantRedis.maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(ConstantRedis.testOnBorrow);
        return jedisPoolConfig;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setPoolConfig(this.jedisPoolConfig());
        factory.setHostName(ConstantRedis.IP);
        factory.setPort(ConstantRedis.PORT);
        factory.setPassword(ConstantRedis.PASSWORD);
        return factory;
    }

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(this.redisConnectionFactory());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(destroyMethod = "destroy")
    public RedisMessageListener redisMessageListener(){
        RedisMessageListener messageListener = new RedisMessageListener();
        messageListener.setRedisTemplate(this.redisTemplate());
        return messageListener;
    }

    @Bean // 配置线程池
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(ConstantRedis.corePoolSize);
        executor.setMaxPoolSize(ConstantRedis.maxPoolSize);
        executor.setQueueCapacity(ConstantRedis.queueCapacity);
        executor.setKeepAliveSeconds(ConstantRedis.keepAliveSeconds);
        executor.setThreadNamePrefix(ConstantRedis.threadNamePrefix);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(destroyMethod = "destroy")
    public RedisMessageListenerContainer redisMessageListenerContainer(){

        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(this.redisConnectionFactory());
        redisMessageListenerContainer.setTaskExecutor(this.executor());

        /**
         * 监听的频道,这个要跟发送消息设定的频道一致,否则会监听不到消息
         */
        redisMessageListenerContainer.addMessageListener(this.redisMessageListener(), new PatternTopic(ConstantRedis.CHANNEL));
        redisMessageListenerContainer.addMessageListener(this.redisMessageListener(), new PatternTopic(ConstantRedis.CHANNEL_TEST));

        return redisMessageListenerContainer;
    }

}
