package com.lanxinbase.app.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alan.luo on 2017/9/24.
 */
@Controller
public class HomeViewController {

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request){
        ModelAndView view = new ModelAndView();


        view.addObject("title","首页");
        view.addObject("keywords","首页,keywords");
        view.addObject("description","首页,description");
        view.setViewName("Home/index");
        return view;
    }
}
