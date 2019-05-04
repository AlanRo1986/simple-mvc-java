package com.lanxinbase.system.service;

import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.service.resource.IKafkaMQService;
import com.lanxinbase.system.utils.NumberUtils;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by alan on 2019/5/4.
 *
 * 0.需要下载Kafka，这里我下载的版本是：kafka_2.12-1.0.1。
 * 1.配置kafka（主要是日志的路径，Socket Server Settings:{
 *      port=9092
 *      host.name=127.0.0.1
 * }）
 * 2.启动zookeeper:zkServer
 * 3.启动Kafka:.\bin\windows\kafka-server-start.bat .\config\server.properties
 *
 * Topic Test:
 *
 * 发送消息:
 *      http://localhost:8180/test/kafka/pub
 *
 * Id=100 & 100 监听:
 *      http://localhost:8180/test/kafka/sub?group=20190504&id=100
 *      http://localhost:8180/test/kafka/sub?group=20190504&id=101
 *
 * 测试日志:
 * 04-May-2019 16:13:00.647 .onMessage 100#{topic:lan_topic, key:app_-1937508585, value:test kafka 1556957580589, offset:113, partition:0, timestamp:1556957580589 }
 *
 * Id=102 监听
 *      http://localhost:8180/test/kafka/sub?group=20190503&id=102
 *
 * 测试日志:
 * 04-May-2019 16:13:06.892 .onMessage 102#{topic:lan_topic, key:app_-1937508585, value:test kafka 1556957580589, offset:113, partition:0, timestamp:1556957580589 }
 * 注：102监听的Topic跟Id=100的是一样的，但是group.id不一样，所有102会收到上一条消息，可以通过时间戳对比
 *
 * ------------------------------------------------------------------------------------------------------------------
 * 发送消息:
 *      http://localhost:8180/test/kafka/pub
 *
 * 测试日志:
 * 04-May-2019 16:13:11.292 .onMessage 102#{topic:lan_topic, key:app_-1936558289, value:test kafka 1556957591240, offset:114, partition:0, timestamp:1556957591240 }
 * 04-May-2019 16:13:11.293 .onMessage 100#{topic:lan_topic, key:app_-1936558289, value:test kafka 1556957591240, offset:114, partition:0, timestamp:1556957591240 }
 * 注：由于100&102的group.id不一致，所以它们都收到了消息，但是为什么101收不到消息呢？因为是100的服务器状态良好，现在我们来取消100的监听
 *
 * ------------------------------------------------------------------------------------------------------------------
 * 取消监听:
 *      http://localhost:8180/test/kafka/cancel?id=100
 *      KafkaMessageListenerContainer.stop();
 *
 * 发送消息:
 *     http://localhost:8180/test/kafka/pub
 *
 * 测试日志:
 * 04-May-2019 16:13:23.147 .onMessage 101#{topic:lan_topic, key:app_-1916183009, value:test kafka 1556957603093, offset:115, partition:0, timestamp:1556957603093 }
 * 04-May-2019 16:13:23.147 .onMessage 102#{topic:lan_topic, key:app_-1916183009, value:test kafka 1556957603093, offset:115, partition:0, timestamp:1556957603093 }
 * 注：这下只有101&102能收到消息了。
 *
 * @See TestKafkaMQController
 */
@Service
public class KafkaMQService extends CompactService implements IKafkaMQService, InitializingBean, DisposableBean {

    private static final String uri = "127.0.0.1:9092";
    public static final String TOPIC_DEFAULT = "lan_topic";
    public static final String ROLE_DEFAULT = "app";

    private final Map<String, Object> producerArg = new HashMap<>();
    private final Map<String, Object> consumerArg = new HashMap<>();

    private DefaultKafkaProducerFactory kafkaProducerFactory;
    private DefaultKafkaConsumerFactory kafkaConsumerFactory;

    private KafkaTemplate kafkaTemplate;

    public KafkaMQService() {

    }

    /**
     * 启动后执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.initArg();
        this.getKafkaProducerFactory();
//        this.getKafkaConsumerFactory();

        kafkaTemplate = this.createKafkaTemplate(TOPIC_DEFAULT, this.getProducerListener());

    }

    /**
     * 生产者监听
     * @return
     */
    private ProducerListener<String, String> getProducerListener() {
        return new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
                StringBuffer sb = new StringBuffer();
                sb.append("success{")
                        .append("topic:" + topic)
                        .append(",partition:" + partition)
                        .append(",key:" + key)
                        .append(",value:" + value)
                        .append("}");
                logger(sb.toString());
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
                StringBuffer sb = new StringBuffer();
                sb.append("error{")
                        .append("topic:" + topic)
                        .append(",partition:" + partition)
                        .append(",key:" + key)
                        .append(",value:" + value)
                        .append("}");
                logger(sb.toString());
            }

            @Override
            public boolean isInterestedInSuccess() {
                return false;
            }
        };
    }

    /**
     * 初始化参数
     */
    private void initArg() {
        producerArg.put("bootstrap.servers", uri);
        producerArg.put("group.id", "100");
        producerArg.put("compression.type", "gzip");
        producerArg.put("reconnect.backoff.ms ", 20000);
        producerArg.put("retry.backoff.ms", 20000);
        producerArg.put("retries", 30);
        producerArg.put("batch.size", "16384");
        producerArg.put("linger.ms", "50");
        producerArg.put("acks", "all");
        producerArg.put("buffer.memory", "33554432");
        producerArg.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerArg.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String groupId = "20190504";
        consumerArg.put("bootstrap.servers", uri);
        consumerArg.put("group.id", groupId);//消费群组，如果需要所有消费者都能接收到消息，则为每个消费者设置不同的群组Id
        consumerArg.put("enable.auto.commit", "false");
        consumerArg.put("auto.commit.interval.ms", "1000");
        consumerArg.put("auto.offset.reset", "latest");
        consumerArg.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerArg.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }


    @Override
    public Map<String, Object> getProducerFactoryArg() {
        return producerArg;
    }

    @Override
    public KafkaTemplate getKafkaTemplate() {
        return this.getKafkaTemplate(TOPIC_DEFAULT);
    }

    @Override
    public KafkaTemplate getKafkaTemplate(String topic) {
        return this.getKafkaTemplate(topic, null);
    }

    @Override
    public KafkaTemplate getKafkaTemplate(String topic, ProducerListener listener) {
        return this.createKafkaTemplate(topic, listener);
    }

    /**
     * 创建一个消息模板
     * @param topic 默认的TOPIC
     * @param listener 生产者监听，如不需要则传入null
     * @return KafkaTemplate
     */
    @Override
    public KafkaTemplate createKafkaTemplate(String topic, ProducerListener listener) {
        if (kafkaTemplate == null) {
            kafkaTemplate = new KafkaTemplate(this.getKafkaProducerFactory());
            kafkaTemplate.setDefaultTopic(TOPIC_DEFAULT);
            kafkaTemplate.setProducerListener(listener);
        }
        return kafkaTemplate;
    }

    /**
     * 发布消息
     * @param topic TopicName
     * @param message 消息字符串，通常为JSON string
     * @return
     */
    @Override
    public boolean send(String topic, String message) {
        return this.send(topic, message, false, 0);
    }

    /**
     * 发布消息
     * @param topic TopicName
     * @param message 消息字符串，通常为JSON string
     * @param isUsePartition 是否使用分区
     * @param partitionNum 分区的数量
     * @return
     */
    @Override
    public boolean send(String topic, String message, boolean isUsePartition, Integer partitionNum) {
        return this.send(topic, message, isUsePartition, partitionNum, ROLE_DEFAULT);
    }

    /**
     * 发布消息
     * @param topic TopicName
     * @param message 消息字符串，通常为JSON string
     * @param isUsePartition 是否使用分区
     * @param partitionNum 分区的数量
     * @param role 用来区分消息key值
     * @return
     */
    @Override
    public boolean send(String topic, String message, boolean isUsePartition, Integer partitionNum, String role) {
        if (role == null) {
            role = ROLE_DEFAULT;
        }

        String key = role + "_" + message.hashCode();
        ListenableFuture<SendResult<String, Object>> result;
        if (isUsePartition) {
            int index = getPartitionIndex(key, partitionNum);
            result = kafkaTemplate.send(topic, index, key, message);

        } else {
            result = kafkaTemplate.send(topic, key, message);
        }

        return checkResult(result);
    }

    /**
     * 检查是否发送成功
     * @param result ListenableFuture
     * @return
     */
    private boolean checkResult(ListenableFuture<SendResult<String, Object>> result) {
        if (result != null) {
            try {
                long offset = result.get().getRecordMetadata().offset();
                if (offset >= 0) {
                    return true;
                }
                logger("unknown offset.");
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger("send time out.", e.getMessage());

            } catch (ExecutionException e) {
                e.printStackTrace();
                logger("send time out.", e.getMessage());
            }
        }
        return false;
    }

    /**
     * 获取分区索引，根据key自动分配
     * @param hashCode key的hashCode
     * @param partitionNum 分区的总数量
     * @return 返回索引号
     */
    @Override
    public int getPartitionIndex(String hashCode, int partitionNum) {
        if (hashCode == null) {
            return NumberUtils.nextInt(partitionNum);
        }
        return Math.abs(hashCode.hashCode()) % partitionNum;
    }

    @Override
    public Map<String, Object> getConsumerFactoryArg() {
        return consumerArg;
    }

    @Override
    public Map<String, Object> setConsumerFactoryArg(String key, Object val) {
        consumerArg.put(key, val);
        return consumerArg;
    }

    /**
     * 添加一个消费者监听
     * @param listener 监听器
     * @param topic 监听Topic名称
     * @return 返回KafkaMessageListenerContainer对象，可以进行stop或start
     */
    @Override
    public KafkaMessageListenerContainer addListener(MessageListener listener, String topic) {
        return this.addListener(listener, getConsumerFactoryArg().get("group.id").toString(), topic);
    }

    /**
     * 添加一个消费者监听
     * @param listener 监听器
     * @param groupId 消费者Id，需要让所有的消费者接收消息，请指定不同的分组Id
     * @param topic 监听Topic名称
     * @return 返回KafkaMessageListenerContainer对象，可以进行stop或start
     */
    @Override
    public KafkaMessageListenerContainer addListener(MessageListener listener, String groupId, String... topic) {
        ContainerProperties properties = new ContainerProperties(topic);
        properties.setMessageListener(listener);
        properties.setGroupId(groupId);

        KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(getKafkaConsumerFactory(), properties);
        container.start();
        return container;
    }

    /**
     * 创建一个生产者工厂类
     * @return
     */
    public DefaultKafkaProducerFactory getKafkaProducerFactory() {
        if (kafkaProducerFactory == null) {
            kafkaProducerFactory = new DefaultKafkaProducerFactory(producerArg);
        }
        return kafkaProducerFactory;
    }

    /**
     * 创建一个消费者工厂类
     * @return
     */
    public DefaultKafkaConsumerFactory getKafkaConsumerFactory() {
        if (kafkaConsumerFactory == null) {
            kafkaConsumerFactory = new DefaultKafkaConsumerFactory(consumerArg);
        }
        return kafkaConsumerFactory;
    }

    @Override
    public void destroy() throws Exception {

    }


}
