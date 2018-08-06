package com.lanxinbase.app.api.controller.Test;


import com.lanxinbase.model.LxUser;
import com.lanxinbase.model.common.WhereModel;
import com.lanxinbase.service.resource.IUserService;
import com.lanxinbase.system.basic.BasicApiController;
import com.lanxinbase.system.core.ResultResp;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.utils.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan.luo on 2017/9/18.
 */

@RestController
public class TestController extends BasicApiController   {

    @Resource
    private IUserService userService;

    public TestController() {
        super(TestController.class);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultResp<Map<String, Object>> get(HttpServletRequest request, HttpServletResponse response) {
        ResultResp<Map<String, Object>> resp = new ResultResp<>();

        try {
            Map<String, Object> data = new HashMap<>();

            data.put("users",userService.getAll(new WhereModel()));
            resp.setData(data);
        } catch (IllegalServiceException e) {
            e.printStackTrace();
        }
        return resp;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public ResultResp<Map<String, Object>> insert(HttpServletRequest request, HttpServletResponse response) {
        ResultResp<Map<String, Object>> resp = new ResultResp<>();

        try {
            Map<String, Object> data = new HashMap<>();
            LxUser user = new LxUser();
            user.setUsername("name"+ DateUtils.getTimeInt());
            user.setCreateTime(DateUtils.getTimeInt());
            data.put("users",userService.insert(user));
            resp.setData(data);
        } catch (IllegalServiceException e) {
            e.printStackTrace();
        }
        return resp;
    }

}
