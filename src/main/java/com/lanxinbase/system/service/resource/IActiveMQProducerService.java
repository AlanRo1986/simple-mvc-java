package com.lanxinbase.system.service.resource;


import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 * Created by alan on 2019/5/1.
 */
public interface IActiveMQProducerService {
    void send(String message);

    void push(String message);
}
