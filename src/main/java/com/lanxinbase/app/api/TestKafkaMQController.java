package com.lanxinbase.app.api;

import com.lanxinbase.system.core.ResultResp;
import com.lanxinbase.system.listener.KafkaMQQueueListener;
import com.lanxinbase.system.service.KafkaMQService;
import com.lanxinbase.system.service.resource.IKafkaMQService;
import com.lanxinbase.system.utils.DateTimeUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by alan on 2019/5/4.
 */

@RestController
@RequestMapping(value = "/test")
public class TestKafkaMQController {

    private static final Logger logger = Logger.getLogger("kafkaMQ>");

    @Autowired
    private IKafkaMQService kafkaMQService;

    private final Map<String, KafkaMessageListenerContainer> listenerContainerMap = new ConcurrentHashMap<>();

    /**
     * http://localhost:8180/test/kafka/pub
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/kafka/pub")
    public ResultResp<Void> kafkaPub(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();

        String msg = "test kafka " + DateTimeUtils.getTime();
        try {
            kafkaMQService.send(KafkaMQService.TOPIC_DEFAULT, msg);
            resp.setInfo(msg);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setInfo(e.getMessage());
        }

        return resp;
    }

    /**
     * http://localhost:8180/test/kafka/sub?group=20190504&id=100
     * http://localhost:8180/test/kafka/sub?group=20190504&id=101
     * http://localhost:8180/test/kafka/sub?group=20190503&id=102
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/kafka/sub")
    public ResultResp<Void> kafkaSub(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();

        String id = request.getParameter("id");
        String group = request.getParameter("group");

        try {
            KafkaMessageListenerContainer container = kafkaMQService.addListener(new MessageListener<String, Object>() {

                @Override
                public void onMessage(ConsumerRecord<String, Object> record) {
                    String log = "%s#{topic:%s, key:%s, value:%s, offset:%s, partition:%s, timestamp:%s }";
                    logger.info(String.format(log, id, record.topic(), record.key(), record.value(), record.offset(), record.partition(), record.timestamp()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, group, KafkaMQService.TOPIC_DEFAULT);

            listenerContainerMap.put(id, container);
            resp.setInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setInfo(e.getMessage());
        }

        return resp;
    }

    /**
     * http://localhost:8180/test/kafka/cancel?id=100
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/kafka/cancel")
    public ResultResp<Void> kafkaCancel(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();

        String id = request.getParameter("id");

        if (listenerContainerMap.containsKey(id)) {
            listenerContainerMap.get(id).stop();
            listenerContainerMap.remove(id);
        }

        return resp;
    }

}
