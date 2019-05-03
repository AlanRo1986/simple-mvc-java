package com.lanxinbase.system.service.resource;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * Created by alan on 2019/5/2.
 */
public interface IRabbitMQService {

    Connection getConnection() throws Exception;

    Channel getChannel() throws Exception;

    Channel getProducer(String name) throws Exception;

    Channel getProducer(String name, String type) throws Exception;

    Channel createProducer(String name, String type, boolean durable) throws Exception;

    boolean send(String name, String message) throws Exception;

    boolean send(String name, String type, String message) throws Exception;

    void addListener(String name, String type,DeliverCallback listener);

    void addListener(String name, String type, boolean autoAck, DeliverCallback listener);

    void addExchangeListener(String exchange, boolean autoAck, DeliverCallback listener);

    void addQueueListener(String queue, boolean autoAck, DeliverCallback listener);


}
