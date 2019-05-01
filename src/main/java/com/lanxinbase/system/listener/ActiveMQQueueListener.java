package com.lanxinbase.system.listener;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * Created by alan on 2019/5/1.
 */

@Component
public class ActiveMQQueueListener implements MessageListener {

    private static final Logger logger = Logger.getLogger(ActiveMQQueueListener.class.getName());

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            logger.info("----------------" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
