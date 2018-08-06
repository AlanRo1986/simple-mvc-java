package com.lanxinbase.system.bus;

import com.lanxinbase.system.annotation.EventBus;
import com.lanxinbase.system.bus.event.LoginOutEvent;
import com.lanxinbase.system.exception.IllegalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by alan.luo on 2017/10/17.
 */
@EventBus
public class LoginOutEventListener implements ApplicationListener<LoginOutEvent> {


    @Async
    @Override
    public void onApplicationEvent(LoginOutEvent event) {
        Integer userId = (Integer) event.getSource();
        if (userId != null && userId > 0) {
//            HwUserLog log = new HwUserLog();
//            log.setUserId(userId);
//            log.setLogType(UserLogServiceImpl.TYPE_LOG);
//            log.setLogContent("退出成功");
//            try {
//                userLogService.insert(log);
//                userTokenService.updateStatusOffByUserId(userId);
//            } catch (IllegalServiceException e) {
//                e.printStackTrace();
//            }
        }

    }
}
