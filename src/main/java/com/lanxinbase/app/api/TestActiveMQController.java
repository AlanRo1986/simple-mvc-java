package com.lanxinbase.app.api;

import com.lanxinbase.system.core.ResultResp;
import com.lanxinbase.system.service.resource.IActiveMQProducerService;
import com.lanxinbase.system.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alan on 2019/5/1.
 */
@RestController
@RequestMapping(value = "/test")
public class TestActiveMQController {

    @Autowired
    private IActiveMQProducerService producerService;

    @RequestMapping(value = "/activemq/queue")
    public ResultResp<Void> testQueue(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();
        String str = "test queue " + DateTimeUtils.getTimeInt();
        producerService.send(str);
        resp.setInfo(str);
        return resp;
    }

    @RequestMapping(value = "/activemq/mqtt")
    public ResultResp<Void> testMqtt(HttpServletRequest request) {
        ResultResp<Void> resp = new ResultResp<>();
        String str = "test mqtt " + DateTimeUtils.getTimeInt();
        producerService.push(str);
        resp.setInfo(str);
        return resp;
    }
}
