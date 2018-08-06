package com.lanxinbase.system.bus.event;

import com.lanxinbase.system.model.SmsModel;

/**
 * Created by alan on 2018/6/25.
 */
public class SmsEvent extends ApplicationUserEvent {
    public SmsEvent(SmsModel source) {
        super(source);
    }
}
