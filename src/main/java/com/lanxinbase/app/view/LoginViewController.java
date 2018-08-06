package com.lanxinbase.app.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alan.luo on 2017/11/3.
 */
@Controller
public class LoginViewController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();

        view.addObject("title","登录");
        view.addObject("keywords","登录,keywords");
        view.addObject("description","登录,description");
        view.setViewName("Login/index");
        return view;
    }
}
