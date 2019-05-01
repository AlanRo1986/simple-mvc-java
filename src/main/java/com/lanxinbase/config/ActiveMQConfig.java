package com.lanxinbase.config;

import com.lanxinbase.system.listener.ActiveMQQueueListener;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.fusesource.mqtt.client.*;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.*;
import java.net.URISyntaxException;

/**
 * Created by alan on 2019/5/1.
 * <p>
 * 0.下载artemis，不是ActiveMQ!!
 * 在ActiveMQ中，这些都是自动的，但是artemis在第一次使用时候需要创建一个broker。
 * 1.创建broker,命令： ./bin/artemis create --user admin --password admin --role admins --allow-anonymous true /opt/arteclsmis
 * 2.启动artemis,命令：/opt/arteclsmis/bin/artemis run
 *
 * @See http://activemq.apache.org/components/artemis/migration
 */
@Configuration
public class ActiveMQConfig implements DisposableBean {

    public static final String TOPIC_DEFAULT = "topic/default";
    public static final String QUEUE_DEFAULT = "queue/default";
    public static final String MQTT_DEFAULT = "mqtt/default";

    private int mqType = 1;//0:queue|1:topic|2:mqtt
    private static final String tcp_uri = "tcp://0.0.0.0:61616";
    private static final String mqtt_uri = "tcp://0.0.0.0:1883";
    private static final String username = "admin";
    private static final String userpasswd = "admin";

    private ActiveMQConnectionFactory factory;

    private Connection connection;

    @Autowired
    private ActiveMQQueueListener listener;

    public ActiveMQConfig() {
        factory = new ActiveMQConnectionFactory(tcp_uri);
        try {
            connection = factory.createConnection();
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    @Bean(destroyMethod = "close")
    public Session activeMQSession() throws JMSException {
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
    }

    /**
     * Queue生产者
     *
     * @param session
     * @return
     * @throws JMSException
     */
    @Bean
    public MessageProducer messageProducer(Session session) throws JMSException {
        if (mqType == 0) {
            return session.createProducer(session.createQueue(QUEUE_DEFAULT));
        }
        return session.createProducer(session.createQueue(TOPIC_DEFAULT));
    }

    /**
     * Queue消费者
     *
     * @param session
     * @return
     * @throws JMSException
     */
    @Bean
    public MessageConsumer messageConsumer(Session session) throws JMSException {
        MessageConsumer consumer = null;
        if (mqType == 0) {
            consumer = session.createConsumer(session.createQueue(QUEUE_DEFAULT));
        } else {
            consumer = session.createConsumer(session.createQueue(TOPIC_DEFAULT));
        }
        consumer.setMessageListener(listener);
        return consumer;
    }

//    如果需要可以同时创建Queue、topic的生产者跟消费者
//
//    /**
//     * test other consumer by topic.
//     * @param session
//     * @return
//     * @throws JMSException
//     */
//    @Bean
//    public MessageConsumer messageConsumer2(Session session) throws JMSException {
//        MessageConsumer consumer = session.createConsumer(session.createQueue(TOPIC_DEFAULT));
//        consumer.setMessageListener((m) -> {
//            TextMessage textMessage = (TextMessage) m;
//            try {
//                System.out.println("=======>" + textMessage.getText());
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        });
//        return consumer;
//    }

    //******************************* MQTT ***********************************

    @Bean(destroyMethod = "")
    public MQTT mqtt() throws URISyntaxException {
        MQTT mqtt = new MQTT();
        mqtt.setHost(mqtt_uri);
        mqtt.setUserName(username);
        mqtt.setPassword(userpasswd);
        return mqtt;
    }

    @Bean(destroyMethod = "disconnect")
    public BlockingConnection createBlockingConnection(MQTT mqtt) throws Exception {
        if (mqType != 2) {
            return null;
        }

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();
        connection.subscribe(new Topic[]{new Topic(MQTT_DEFAULT, QoS.AT_LEAST_ONCE)});
//        connection.publish();
        new Thread(() -> {
            while (connection.isConnected()) {
                try {
                    Message message = connection.receive();
                    System.out.println("->" + new String(message.getPayload()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return connection;
    }

    @Override
    public void destroy() throws Exception {
        connection.stop();
        connection.close();
    }

//    Test api.
//    @Autowired
//    private IActiveMQProducerService producerService;
//
//    @RequestMapping(value = "/activemq/queue")
//    public ResultResp<Void> testQueue(HttpServletRequest request) {
//        ResultResp<Void> resp = new ResultResp<>();
//        String str = "test queue " + DateTimeUtils.getTimeInt();
//        producerService.send(str);
//        resp.setInfo(str);
//        return resp;
//    }
//
//    @RequestMapping(value = "/activemq/mqtt")
//    public ResultResp<Void> testMqtt(HttpServletRequest request) {
//        ResultResp<Void> resp = new ResultResp<>();
//        String str = "test mqtt " + DateTimeUtils.getTimeInt();
//        producerService.push(str);
//        resp.setInfo(str);
//        return resp;
//    }
}
