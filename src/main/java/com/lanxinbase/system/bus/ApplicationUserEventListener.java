package com.lanxinbase.system.bus;

import com.lanxinbase.system.annotation.EventBus;
import com.lanxinbase.system.bus.event.ApplicationUserEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by alan.luo on 2017/10/17.
 */
@EventBus
public class ApplicationUserEventListener implements ApplicationListener<ApplicationUserEvent> {

    @Async
    @Override
    public void onApplicationEvent(ApplicationUserEvent event) {
//        System.out.println("ApplicationUserInterestedParty:"+event.getSource());

    }
}
