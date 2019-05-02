package com.lanxinbase.system.service.resource;

import javax.jms.*;

/**
 * Created by alan on 2019/5/2.
 */
public interface IActiveMQService {

    Session createSession() throws JMSException;

    Queue createQueue(Session session,String name) throws JMSException;

    Topic createTopic(Session session,String name) throws JMSException;

    MessageProducer getMessageProducer(Queue queue) throws JMSException;

    MessageProducer getMessageProducer(Topic topic) throws JMSException;

    MessageProducer createMessageProducer(Destination destination, String name) throws JMSException;

    MessageConsumer getMessageConsumer(Session session,Queue queue) throws JMSException;

    MessageConsumer getMessageConsumer(Session session,Topic topic) throws JMSException;

    MessageConsumer createMessageConsumer(Session session,Destination destination, String name) throws JMSException;

    boolean send(String name, String type, String message);

    boolean send(MessageProducer producer, String message);

    void addListener(String name, String type, MessageListener listener);


}
