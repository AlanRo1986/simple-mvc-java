package com.lanxinbase.system.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by alan on 2019/5/4.
 */
@Component
public class KafkaMQQueueListener implements MessageListener<String, Object> {

    private static final Logger logger = Logger.getLogger("kafka listener >");


    /**
     * Invoked with data from kafka.
     *
     * @param record the data to be processed.
     */
    @Override
    public void onMessage(ConsumerRecord<String, Object> record) {
        String log = "{topic:%s, key:%s, value:%s, offset:%s, partition:%s, timestamp:%s }";
        logger.info(String.format(log, record.topic(), record.key(), record.value(), record.offset(), record.partition(), record.timestamp()));
    }
}
