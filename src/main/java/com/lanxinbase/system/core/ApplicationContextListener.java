package com.lanxinbase.system.core;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;

/**
 * Created by alan.luo on 2017/9/19.
 */
public class ApplicationContextListener extends ContextLoaderListener {

    public ApplicationContextListener() {
        super();
    }

    public ApplicationContextListener(WebApplicationContext context) {
        super(context);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        WebApplicationContext context = (WebApplicationContext) event.getServletContext()
                .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        Application.getInstance(context).run();

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);

        WebApplicationContext context = (WebApplicationContext) event.getServletContext()
                .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        Application.getInstance(context).stop();
    }
}
