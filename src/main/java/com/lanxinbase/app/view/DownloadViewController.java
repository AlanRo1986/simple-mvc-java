package com.lanxinbase.app.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alan.luo on 2017/9/24.
 */
@Controller
public class DownloadViewController {

    @RequestMapping(value = "/download/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        //Redirect to /WEB-INF/jsp/views/Download/index.jsp
        return "Download/index";
    }

    @RequestMapping(value = "/Download/main",method = RequestMethod.GET)
    public String main(HttpServletRequest request){
        return "Download/main";
    }
}
