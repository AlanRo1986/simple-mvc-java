package com.lanxinbase.app.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alan.luo on 2017/11/3.
 */
@Controller
public class RegisterViewController {

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();

        view.addObject("title","注册");
        view.addObject("keywords","注册,keywords");
        view.addObject("description","注册,description");
        view.setViewName("Register/index");
        return view;
    }
}
