package com.lanxinbase.system.bus.event;

/**
 * Created by alan.luo on 2017/10/16.
 */
public class LoginOutEvent extends ApplicationUserEvent {

    public LoginOutEvent(Integer userId) {
        super(userId);
    }

}
