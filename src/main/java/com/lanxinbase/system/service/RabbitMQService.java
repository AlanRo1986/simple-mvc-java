package com.lanxinbase.system.service;

import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.service.resource.IRabbitMQService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alan on 2019/5/3.
 * <p>
 * <p>
 * 0.需要下载Erlang，并且设置好ERLANG_HOME的环境变量，类似于JDK的配置方式。
 * 1.下载RabbitMQ
 * 3.运行RabbitMQ，like this:./sbin/rabbitmq-server.bat
 * <p>
 * Queue Test:
 * 生产者:
 * http://localhost:8180/test/rabbitmq/pub/add?name=lan.queue&type=queue
 * <p>
 * rabbitMQService.send(name, type, msg);
 * <p>
 * 消费者(3个):
 * http://localhost:8180/test/rabbitmq/sub/add?id=100&name=lan.queue&type=queue
 * http://localhost:8180/test/rabbitmq/sub/add?id=101&name=lan.queue&type=queue
 * http://localhost:8180/test/rabbitmq/sub/add?id=102&name=lan.queue&type=queue
 * <p>
 * rabbitMQService.addListener(name, type, (s, c) -> {
 * logger.info(id + "# message:" + new String(c.getBody()) + ", routing:" + c.getEnvelope().getRoutingKey());
 * });
 * <p>
 * Queue运行日志：
 * 03-May-2019 22:24:37.773 lambda$topic$0 101# message:test RabbitMQ queue lan.queue 1556893477772, routing:lan.queue
 * 03-May-2019 22:24:38.467 lambda$topic$0 102# message:test RabbitMQ queue lan.queue 1556893478466, routing:lan.queue
 * 03-May-2019 22:24:39.376 lambda$topic$0 100# message:test RabbitMQ queue lan.queue 1556893479374, routing:lan.queue
 * <p>
 * 这里生产者生产了3条信息，Queue消息不会丢失，如果生产者生产消息的时候没有消费者进入，那么消息会等到消费者进入后发送给消费者。
 * 如果有多个消费者监听同一个Queue，那么则会按照某种算法，将消息发送给其中一个消费者，如果接收成功后，通道会自动删除消息。
 * <p>
 * Exchange Test:
 * 生产者:
 * http://localhost:8180/test/rabbitmq/pub/add?name=lan.fanout&type=fanout
 * http://localhost:8180/test/rabbitmq/pub/add?name=lan.topic&type=topic
 * <p>
 * rabbitMQService.send(name, type, msg);
 * <p>
 * 消费者:
 * http://localhost:8180/test/rabbitmq/sub/add?id=103&name=lan.fanout&type=fanout
 * http://localhost:8180/test/rabbitmq/sub/add?id=104&name=lan.fanout&type=fanout
 * http://localhost:8180/test/rabbitmq/sub/add?id=105&name=lan.fanout&type=fanout
 * <p>
 * http://localhost:8180/test/rabbitmq/sub/add?id=106&name=lan.topic&type=topic
 * http://localhost:8180/test/rabbitmq/sub/add?id=107&name=lan.topic&type=topic
 * http://localhost:8180/test/rabbitmq/sub/add?id=108&name=lan.topic&type=topic
 * <p>
 * rabbitMQService.addListener(name, type, (s, c) -> {
 * logger.info(id + "# message:" + new String(c.getBody()) + ", routing:" + c.getEnvelope().getRoutingKey());
 * });
 * <p>
 * Exchange运行日志:
 * 03-May-2019 22:24:42.424 lambda$topic$0 104# message:test RabbitMQ fanout lan.fanout 1556893482420, routing:
 * 03-May-2019 22:24:42.425 lambda$topic$0 103# message:test RabbitMQ fanout lan.fanout 1556893482420, routing:
 * 03-May-2019 22:24:42.425 lambda$topic$0 105# message:test RabbitMQ fanout lan.fanout 1556893482420, routing:
 * <p>
 * 03-May-2019 22:24:46.077 lambda$topic$0 107# message:test RabbitMQ topic lan.topic 1556893486075, routing:
 * 03-May-2019 22:24:46.077 lambda$topic$0 108# message:test RabbitMQ topic lan.topic 1556893486075, routing:
 * 03-May-2019 22:24:46.077 lambda$topic$0 106# message:test RabbitMQ topic lan.topic 1556893486075, routing:
 * <p>
 * 从日志时间上可以看的出，生产者的消息，全部同时发送给了所有消费者。如果生产者生产消息的时候没有消费者进入，那么消息会丢失。
 * 当有消费者监听Topic时，可以收到消息，如果同时有多个消费者监听同一个topic，那么消息将分别发送给各个消费者。
 *
 * @See TestRabbitMQController
 */

@Service
public class RabbitMQService extends CompactService implements InitializingBean, DisposableBean, IRabbitMQService {

    private static final String host = "127.0.0.1";
    private static final int prot = 5672;

    public static final String TOPIC_DEFAULT = "lan.topic";
    public static final String DIRECT_DEFAULT = "lan.direct";
    public static final String HEADERS_DEFAULT = "lan.headers";
    public static final String FANOUT_DEFAULT = "lan.fanout";

    public static final String QUEUE_DEFAULT = "lan.queue";

    public static final String direct = "direct";
    public static final String topic = "topic";
    public static final String fanout = "fanout";
    public static final String headers = "headers";
    public static final String queue = "queue";

    /**
     * 缓存Chanel
     */
    private Map<String, Channel> producerMap = new ConcurrentHashMap<>();

    /**
     * 连接工厂
     */
    private ConnectionFactory factory;

    private Connection connection;
    private Channel channel;

    public RabbitMQService() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(prot);
    }

    /**
     * 获取一个连接，如果为空或断开了连接则重新实例化
     *
     * @return Connection
     * @throws Exception
     */
    @Override
    public Connection getConnection() throws Exception {
        if (connection == null || !connection.isOpen()) {
            connection = factory.newConnection();
        }
        return connection;
    }

    /**
     * 返回一个通道
     *
     * @return Channel
     * @throws Exception
     */
    @Override
    public Channel getChannel() throws Exception {
        if (channel == null || !channel.isOpen()) {
            channel = this.getConnection().createChannel();
        }
        return channel;
    }

    /**
     * 获取一个生产者
     *
     * @param name Queue name|exchange name
     * @return Channel
     * @throws Exception
     */
    @Override
    public Channel getProducer(String name) throws Exception {
        return this.getProducer(name, queue);
    }

    /**
     * 获取一个生产者
     *
     * @param name Queue name|exchange name
     * @param type queue|fanout|topic|headers|direct
     * @return Channel
     * @throws Exception
     */
    @Override
    public Channel getProducer(String name, String type) throws Exception {
        return this.createProducer(name, type, false);
    }

    /**
     * 创建一个生产者，如果缓存中没有，则重新创建
     *
     * @param exchange Queue name|exchange name
     * @param type     queue|fanout|topic|headers|direct
     * @param durable  是否持久性
     * @return Channel
     * @throws Exception
     */
    @Override
    public Channel createProducer(String exchange, String type, boolean durable) throws Exception {
        if (producerMap.containsKey(exchange + type + durable)) {
            logger("producer by cache.");
            Channel c1 = producerMap.get(exchange + type + durable);
            if (c1.isOpen()) {
                return c1;
            }
        }

        Channel c = this.getChannel();
        if (type == null || queue.equals(type)) {
            c.queueDeclare(exchange, durable, false, false, null);
        } else {
            c.exchangeDeclare(exchange, type, durable);
        }
        producerMap.put(exchange + type + durable, c);
        return c;
    }


    /**
     * 发送一条消息,默认只发送queue消息
     *
     * @param name    Queue name|exchange name
     * @param message content
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean send(String name, String message) throws Exception {
        return this.send(name, queue, message);
    }

    /**
     * 发送一条消息
     *
     * @param name    Queue name|exchange name
     * @param type    queue|fanout|topic|headers|direct
     * @param message content
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean send(String name, String type, String message) throws Exception {
        try {
            if (type == null || queue.equals(type)) {
                this.getProducer(name, type).basicPublish("", name, null, message.getBytes());
            } else {
                this.getProducer(name, type).basicPublish(name, "", null, message.getBytes());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置消费者监听
     *
     * @param name     Queue name|exchange name
     * @param type     queue|fanout|topic|headers|direct
     * @param listener DeliverCallback监听器
     */
    @Override
    public void addListener(String name, String type, DeliverCallback listener) {
        this.addListener(name, type, true, listener);
    }

    /**
     * 设置消费者监听
     *
     * @param name     Queue name|exchange name
     * @param type     queue|fanout|topic|headers|direct
     * @param autoAck  是否自动响应
     * @param listener DeliverCallback监听器
     */
    @Override
    public void addListener(String name, String type, boolean autoAck, DeliverCallback listener) {
        if (type == null || queue.equals(type)) {
            this.addQueueListener(name, autoAck, listener);
        } else {
            this.addExchangeListener(name, autoAck, listener);
        }
    }

    /**
     * 添加一个exchange监听器
     *
     * @param exchange exchange name
     * @param autoAck  是否自动响应
     * @param listener DeliverCallback监听器
     */
    @Override
    public void addExchangeListener(String exchange, boolean autoAck, DeliverCallback listener) {
        try {
            Channel c = this.getChannel();
            String queue = c.queueDeclare().getQueue();
            c.queueBind(queue, exchange, "");
            c.basicConsume(queue, autoAck, listener, e -> {
                logger("exchange error:" + e);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个Queue监听器
     *
     * @param queue    Queue name
     * @param autoAck  是否自动响应
     * @param listener DeliverCallback监听器
     */
    @Override
    public void addQueueListener(String queue, boolean autoAck, DeliverCallback listener) {
        try {
            this.getProducer(queue).basicConsume(queue, autoAck, listener, e -> {
                logger("queue error:" + e);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        channel.close();
        connection.close();
    }
}
