package com.lanxinbase.app.api;

import com.lanxinbase.system.core.ResultResp;
import com.lanxinbase.system.service.resource.IActiveMQService;
import com.lanxinbase.system.utils.DateTimeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by alan on 2019/5/1.
 */
@RestController
@RequestMapping(value = "/test")
public class TestActiveMQController {

    private static final Logger logger = Logger.getLogger("TestActiveMQController>");

    @Resource
    private IActiveMQService activeMQService;


    /**
     * /test/activemq/pub/add?name=testQueue&type=queue
     * /test/activemq/pub/add?name=testQueue1&type=queue
     * <p>
     * /test/activemq/pub/add?name=testTopic&type=topic
     * /test/activemq/pub/add?name=testTopic1&type=topic
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/activemq/pub/add")
    public ResultResp<Void> pubAdd(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();

        String name = request.getParameter("name");
        String type = request.getParameter("type");

        String msg = "test mqtt " + DateTimeUtils.getTimeInt();

        activeMQService.send(name, type, msg);

        resp.setInfo(msg);
        return resp;
    }

    /**
     * /test/activemq/sub/add?id=100&name=testQueue&type=queue
     * /test/activemq/sub/add?id=101&name=testQueue1&type=queue
     * <p>
     * /test/activemq/sub/add?id=100&name=testTopic&type=topic
     * /test/activemq/sub/add?id=101&name=testTopic&type=topic
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/activemq/sub/add")
    public ResultResp<Void> subAdd(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String type = request.getParameter("type");

        activeMQService.addListener(name, type, (m) -> {
            TextMessage textMessage = (TextMessage) m;
            try {
                logger.info(id + " : " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        return resp;
    }
}
