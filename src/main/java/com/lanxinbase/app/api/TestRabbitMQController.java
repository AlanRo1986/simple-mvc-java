package com.lanxinbase.app.api;

import com.lanxinbase.system.core.ResultResp;
import com.lanxinbase.system.service.resource.IRabbitMQService;
import com.lanxinbase.system.utils.DateTimeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by alan on 2019/5/1.
 */
@RestController
@RequestMapping(value = "/test")
public class TestRabbitMQController {

    private static final Logger logger = Logger.getLogger("RabbitMQ>");

    @Resource
    private IRabbitMQService rabbitMQService;


    /**
     * http://localhost:8180/test/rabbitmq/pub/add?name=lan.queue&type=queue
     * http://localhost:8180/test/rabbitmq/pub/add?name=lan.fanout&type=fanout
     * http://localhost:8180/test/rabbitmq/pub/add?name=lan.topic&type=topic
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/rabbitmq/pub/add")
    public ResultResp<Void> queue(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();

        String name = request.getParameter("name");
        String type = request.getParameter("type");

        String msg = "test RabbitMQ " + type + " " + name + " " + DateTimeUtils.getTime();
        try {
            rabbitMQService.send(name, type, msg);
            resp.setInfo(msg);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setInfo(e.getMessage());
        }

        return resp;
    }

    /**
     * http://localhost:8180/test/rabbitmq/sub/add?id=100&name=lan.queue&type=queue
     * http://localhost:8180/test/rabbitmq/sub/add?id=101&name=lan.queue&type=queue
     * http://localhost:8180/test/rabbitmq/sub/add?id=102&name=lan.queue&type=queue
     *
     * http://localhost:8180/test/rabbitmq/sub/add?id=103&name=lan.fanout&type=fanout
     * http://localhost:8180/test/rabbitmq/sub/add?id=104&name=lan.fanout&type=fanout
     * http://localhost:8180/test/rabbitmq/sub/add?id=105&name=lan.fanout&type=fanout
     *
     * http://localhost:8180/test/rabbitmq/sub/add?id=106&name=lan.topic&type=topic
     * http://localhost:8180/test/rabbitmq/sub/add?id=107&name=lan.topic&type=topic
     * http://localhost:8180/test/rabbitmq/sub/add?id=108&name=lan.topic&type=topic
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/rabbitmq/sub/add")
    public ResultResp<Void> topic(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        try {
            rabbitMQService.addListener(name, type, (s, c) -> {
                logger.info(id + "# message:" + new String(c.getBody()) + ", routing:" + c.getEnvelope().getRoutingKey());
            });
            resp.setInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setInfo(e.getMessage());
        }

        return resp;
    }
}
