package com.lanxinbase.system.bus.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by alan.luo on 2017/10/16.
 */
public class ApplicationUserEvent extends ApplicationEvent {
    public ApplicationUserEvent(Object source) {
        super(source);

    }
}
