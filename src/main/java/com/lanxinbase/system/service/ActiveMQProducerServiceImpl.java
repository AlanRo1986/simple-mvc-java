package com.lanxinbase.system.service;

import com.lanxinbase.config.ActiveMQConfig;
import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.service.resource.IActiveMQProducerService;
import org.apache.activemq.artemis.jms.client.ActiveMQMessageProducer;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.QoS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by alan on 2019/5/1.
 */
//@Service
@Deprecated
public class ActiveMQProducerServiceImpl extends CompactService implements IActiveMQProducerService {

//    @Autowired
    private MessageProducer producer;

//    @Autowired
    private Session session;

//    @Autowired
    private BlockingConnection blockingConnection;


    public ActiveMQProducerServiceImpl() {

    }

    /**
     * Queue & Topic send
     *
     * @param message
     */
    @Override
    public void send(String message) {
        try {
            producer.send(session.createTextMessage(message));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * MQTT push
     *
     * @param message
     */
    @Override
    public void push(String message) {
        try {
            blockingConnection.publish(ActiveMQConfig.MQTT_DEFAULT, message.getBytes(), QoS.AT_LEAST_ONCE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
