package com.lanxinbase.app.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alan.luo on 2017/11/6.
 */
@Controller
public class ArticleViewController {

    @RequestMapping(value = "/article/{id}",method = RequestMethod.GET)
    public ModelAndView index(@PathVariable String id, HttpServletRequest request){
        ModelAndView view = new ModelAndView();

        view.addObject("title","文章内容");
        view.addObject("keywords","文章内容,keywords");
        view.addObject("description","文章内容,description");
        view.setViewName("Article/index");
        return view;
    }

    @RequestMapping(value = "/article/add/{id}",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable String id, HttpServletRequest request){
        ModelAndView view = new ModelAndView();

        String title = Integer.valueOf(id) > 0 ? "编辑文章" : "发表文章";
        view.addObject("title",title);
        view.addObject("keywords",title+",keywords");
        view.addObject("description",title+",description");
        view.setViewName("Article/add");
        return view;
    }
}
