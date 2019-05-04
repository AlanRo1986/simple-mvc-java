package com.lanxinbase.system.service.resource;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.ProducerListener;

import java.util.Map;

/**
 * Created by alan on 2019/5/4.
 */
public interface IKafkaMQService {

    Map<String, Object> getProducerFactoryArg();

    KafkaTemplate getKafkaTemplate();

    KafkaTemplate getKafkaTemplate(String topic);

    KafkaTemplate getKafkaTemplate(String topic, ProducerListener listener);

    KafkaTemplate createKafkaTemplate(String topic, ProducerListener listener);

    boolean send(String topic, String message);

    boolean send(String topic, String message, boolean isUsePartition, Integer partitionNum);

    boolean send(String topic, String message, boolean isUsePartition, Integer partitionNum, String role);

    int getPartitionIndex(String hashCode, int partitionNum);

    Map<String, Object> getConsumerFactoryArg();

    Map<String, Object> setConsumerFactoryArg(String key, Object val);

    KafkaMessageListenerContainer addListener(MessageListener listener, String topic);

    KafkaMessageListenerContainer addListener(MessageListener listener, String groupId, String... topic);

}
