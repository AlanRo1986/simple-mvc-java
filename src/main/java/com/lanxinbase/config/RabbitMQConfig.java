package com.lanxinbase.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * Created by alan on 2019/5/3.
 */
//@Configuration
@Deprecated
public class RabbitMQConfig {

    private static final Logger logger = Logger.getLogger("RabbitMQ--->");

    private static final String host = "127.0.0.1";

    public static final String TOPIC_DEFAULT = "lan.topic";
    public static final String QUEUE_DEFAULT = "lan.queue";

    @Bean(name = "createRabbitMQConnectionFactory")
    public ConnectionFactory createRabbitMQConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        return factory;
    }

    @Bean(name = "createRabbitConnection")
    public Connection createRabbitConnection(ConnectionFactory factory) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        return connection;
    }

    @Bean(name = "createRabbitMQChannel")
    public Channel createRabbitMQChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
//        channel.queueDeclare(QUEUE_DEFAULT, false, false, false, null);

//        String queue = channel.queueDeclare().getQueue();
//        channel.exchangeDelete(TOPIC_DEFAULT);
        channel.exchangeDeclare(TOPIC_DEFAULT, "fanout", false);//非持久性
//        channel.queueBind(queue, TOPIC_DEFAULT, "");
//        channel.basicQos(2);
//        this.sub(channel);
        this.sub1(channel);
        return channel;
    }

    private void sub(Channel channel) {
        try {
            channel.basicConsume(QUEUE_DEFAULT, true, (s, c) -> {
                logger.info("Id100> message:" + new String(c.getBody()) + ",routing:" + c.getEnvelope().getRoutingKey());
            }, (s) -> {
                logger.info("err100:" + s);
            });

            channel.basicConsume(QUEUE_DEFAULT, true, (s, c) -> {
                logger.info("Id101> message:" + new String(c.getBody()) + ",routing:" + c.getEnvelope().getRoutingKey());
            }, (s) -> {
                logger.info("err101:" + s);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sub1(Channel channel) {
        try {
            String queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, TOPIC_DEFAULT, "");
            channel.basicConsume(queue, true, (s, c) -> {
                logger.info("Id200> message:" + new String(c.getBody()) + ",routing:" + c.getEnvelope().getRoutingKey());
            }, (s) -> {
                logger.info("err200:" + s);
            });


            queue = channel.queueDeclare().getQueue();
            channel.queueBind(queue, TOPIC_DEFAULT, "");
            channel.basicConsume(queue, true, (s, c) -> {
                logger.info("Id201> message:" + new String(c.getBody()) + ",routing:" + c.getEnvelope().getRoutingKey());
            }, (s) -> {
                logger.info("err201:" + s);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
