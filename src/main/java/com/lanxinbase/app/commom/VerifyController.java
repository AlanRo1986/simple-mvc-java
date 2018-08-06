package com.lanxinbase.app.commom;

import com.lanxinbase.system.basic.BasicApiController;
import com.lanxinbase.system.provider.ImageVerifyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alan on 2018/7/24.
 */
@RestController
public class VerifyController {

    @Autowired
    private ImageVerifyProvider verifyProvider;

    @RequestMapping(value = "/getVerify", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            verifyProvider.make(response, request.getSession());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
