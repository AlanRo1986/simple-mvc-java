package com.lanxinbase.system.service;

import com.lanxinbase.system.basic.CompactService;
import com.lanxinbase.system.service.resource.IActiveMQService;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by alan on 2019/5/2.
 * <p>
 * * 0.下载artemis，不是ActiveMQ!!
 * 在ActiveMQ中，这些都是自动的，但是artemis在第一次使用时候需要创建一个broker。
 * 1.创建broker,命令： ./bin/artemis create --user admin --password admin --role admins --allow-anonymous true /opt/arteclsmis
 * 2.启动artemis,命令：/opt/arteclsmis/bin/artemis run
 * <p>
 * <p>
 * Queue Test:
 * 生产者:
 * http://localhost:8180/test/activemq/pub/add?name=testQueue&type=queue
 * http://localhost:8180/test/activemq/pub/add?name=testQueue1&type=queue
 * http://localhost:8180/test/activemq/pub/add?name=testQueue2&type=queue
 * <p>
 * 消费者：
 * http://localhost:8180/test/activemq/sub/add?id=100&name=testQueue&type=queue
 * http://localhost:8180/test/activemq/sub/add?id=101&name=testQueue&type=queue
 * http://localhost:8180/test/activemq/sub/add?id=102&name=testQueue2&type=queue
 * http://localhost:8180/test/activemq/sub/add?id=103&name=testQueue1&type=queue
 * http://localhost:8180/test/activemq/sub/add?id=104&name=testQueue&type=queue
 * <p>
 * 结果日志：
 * 02-May-2019 13:32:22.318 lambda$subAdd$0 101 : test mqtt 1556775142
 * 02-May-2019 13:32:22.988 lambda$subAdd$0 100 : test mqtt 1556775142
 * 02-May-2019 13:32:23.824 lambda$subAdd$0 104 : test mqtt 1556775143
 * <p>
 * Queue消息不会丢失，如果生产者生产消息的时候没有消费者进入，那么消息会等到消费者进入后发送给消费者。
 * 如果有多个消费者监听同一个Queue，那么则会按照某种算法，将消息发送给其中一个消费者，如果接收成功后，通道会自动删除消息。
 * <p>
 * ***************************************************************************************************************
 * <p>
 * Topic Test:
 * 生产者:
 * http://localhost:8180/test/activemq/pub/add?name=testTopic&type=topic
 * http://localhost:8180/test/activemq/pub/add?name=testTopic1&type=topic
 * http://localhost:8180/test/activemq/pub/add?name=testTopic2&type=topic
 * <p>
 * 消费者：
 * http://localhost:8180/test/activemq/sub/add?id=100&name=testTopic&type=topic
 * http://localhost:8180/test/activemq/sub/add?id=101&name=testTopic&type=topic
 * http://localhost:8180/test/activemq/sub/add?id=102&name=testTopic1&type=topic
 * http://localhost:8180/test/activemq/sub/add?id=103&name=testTopic2&type=topic
 * http://localhost:8180/test/activemq/sub/add?id=104&name=testTopic&type=topic
 * <p>
 * 结果日志：
 * 02-May-2019 13:39:53.216 信息  topic/default/testTopic
 * 02-May-2019 13:39:53.219 lambda$subAdd$0 101 : test mqtt 1556775593
 * 02-May-2019 13:39:53.219 lambda$subAdd$0 100 : test mqtt 1556775593
 * 02-May-2019 13:39:53.220 lambda$subAdd$0 104 : test mqtt 1556775593
 * <p>
 * 02-May-2019 13:39:56.224 信息 topic/default/testTopic1
 * 02-May-2019 13:39:56.227 lambda$subAdd$0 102 : test mqtt 1556775596
 * <p>
 * 02-May-2019 13:39:59.420 信息 topic/default/testTopic2
 * 02-May-2019 13:39:59.423 lambda$subAdd$0 103 : test mqtt 1556775599
 * <p>
 * Topic消息会丢失，如果生产者生产消息的时候没有消费者进入，那么消息会丢失。
 * 当有消费者监听Topic时，可以收到消息，如果同时有多个消费者监听同一个topic，那么消息将分别发送给各个消费者。
 *
 * @See TestActiveMQController.class
 */

@Service
public class ActiveMQService extends CompactService implements IActiveMQService, InitializingBean, DisposableBean {

    public static final String TYPE_QUEUE = "queue";
    public static final String TYPE_TOPIC = "topic";

    private static final String TOPIC_DEFAULT = "topic/default/";
    private static final String QUEUE_DEFAULT = "queue/default/";
    private static final String tcp_uri = "tcp://0.0.0.0:61616";

    private ActiveMQConnectionFactory factory;
    private Connection connection;
    private Session session;

    private Map<String, MessageProducer> producerMap = new ConcurrentHashMap<>();

    public ActiveMQService() {

    }

    /**
     * 首次启动运行
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        factory = new ActiveMQConnectionFactory(tcp_uri);
        factory.setCacheDestinations(true);

        try {
            connection = factory.createConnection();
            connection.start();
            createSession();
        }catch (JMSException e){
            e.printStackTrace();
        }
    }

    /**
     * 私有的session，主要用于生产者
     *
     * @return
     * @throws JMSException
     */
    private Session getSession() throws JMSException {
        if (session == null) {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        return session;
    }

    /**
     * 创建一个新的session，主要应用于消费者
     *
     * @return
     * @throws JMSException
     */
    @Override
    public Session createSession() throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    /**
     * 创建一个Queue
     *
     * @param s    session
     * @param name 如：test
     * @return
     * @throws JMSException
     */
    @Override
    public Queue createQueue(Session s, String name) throws JMSException {
        if (name == null) {
            name = "";
        }
        return s.createQueue(QUEUE_DEFAULT + name);
    }

    /**
     * 创建一个Topic
     *
     * @param s    session
     * @param name 如：test
     * @return
     * @throws JMSException
     */
    @Override
    public Topic createTopic(Session s, String name) throws JMSException {
        if (name == null) {
            name = "";
        }
        return s.createTopic(TOPIC_DEFAULT + name);
    }

    /**
     * 获取一个生产者
     *
     * @param queue
     * @return
     * @throws JMSException
     */
    @Override
    public MessageProducer getMessageProducer(Queue queue) throws JMSException {
        return createMessageProducer(queue, queue.getQueueName());
    }

    /**
     * 获取一个生产者
     *
     * @param topic
     * @return
     * @throws JMSException
     */
    @Override
    public MessageProducer getMessageProducer(Topic topic) throws JMSException {
        return createMessageProducer(topic, topic.getTopicName());
    }

    /**
     * 创建一个生产者
     *
     * @param destination
     * @param name        缓存key，同时等于queue&topic的name
     * @return
     * @throws JMSException
     */
    @Override
    public MessageProducer createMessageProducer(Destination destination, String name) throws JMSException {
        logger(name);
        MessageProducer producer = producerMap.get(name);
        if (producer == null) {
            producer = session.createProducer(destination);
            producerMap.put(name, producer);
        }
        return producer;
    }

    /**
     * 获取一个消费者
     *
     * @param s     可以通过createSession创建
     * @param queue
     * @return
     * @throws JMSException
     */
    @Override
    public MessageConsumer getMessageConsumer(Session s, Queue queue) throws JMSException {
        return createMessageConsumer(s, queue, queue.getQueueName());
    }

    /**
     * 获取一个消费者
     *
     * @param s     可以通过createSession创建
     * @param topic
     * @return
     * @throws JMSException
     */
    @Override
    public MessageConsumer getMessageConsumer(Session s, Topic topic) throws JMSException {
        return createMessageConsumer(s, topic, topic.getTopicName());
    }

    /**
     * 创建一个消费者
     *
     * @param s           session（本来是把session做成单例，但是消费者应该是动态的，不同于生产者，所以这里需要随时创建一个session）
     * @param destination
     * @param name        废弃
     * @return
     * @throws JMSException
     */
    @Override
    public MessageConsumer createMessageConsumer(Session s, Destination destination, String name) throws JMSException {
//        MessageConsumer consumer = consumerMap.get(name);
        MessageConsumer consumer = null;
//        if (consumer == null) {
//            consumer = session.createConsumer(destination);
//            consumerMap.put(name, consumer);
//        }
        consumer = s.createConsumer(destination);
        return consumer;
    }

    /**
     * 生产一条消息
     *
     * @param name    Queue|Topic的name
     * @param type    类型：Queue|Topic
     * @param message 字符串消息，通常应该是JSON字符串
     * @return
     */
    @Override
    public boolean send(String name, String type, String message) {
        return this.send(name, type, message, null);
    }

    @Override
    public boolean send(String name, String type, String message, CompletionListener listener) {
        MessageProducer producer;
        try {
            if (TYPE_QUEUE.equals(type)) {
                producer = getMessageProducer(createQueue(getSession(), name));
            } else {
                producer = getMessageProducer(createTopic(getSession(), name));
            }
            if (listener == null) {
                return this.send(producer, message);
            } else {
                return this.send(producer, message, listener);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 发送消息
     *
     * @param producer 生产者
     * @param message  消息字符串
     * @return
     */
    @Override
    public boolean send(MessageProducer producer, String message) {
        try {
            producer.send(session.createTextMessage(message));
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean send(MessageProducer producer, String message, CompletionListener listener) {
        try {
            producer.send(session.createTextMessage(message), listener);
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 消费者监听
     *
     * @param name     Queue|Topic的name
     * @param type     类型：Queue|Topic
     * @param listener 监听回调
     */
    @Override
    public void addListener(String name, String type, MessageListener listener) {
        MessageConsumer consumer = null;
        try {
            Session session = createSession();
            if (TYPE_QUEUE.equals(type)) {
                consumer = getMessageConsumer(session, createQueue(session, name));
            } else {
                consumer = getMessageConsumer(session, createTopic(session, name));
            }
            this.addListener(consumer, listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void addListener(MessageConsumer consumer, MessageListener listener) {
        try {
            consumer.setMessageListener(listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 程序退出时需要关闭或停止连接
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (session != null) {
            session.close();
        }
        connection.stop();
        connection.close();
        factory.close();
    }


}
