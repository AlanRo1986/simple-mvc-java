package com.lanxinbase.system.bus;


import com.lanxinbase.system.annotation.EventBus;
import com.lanxinbase.system.bus.event.RegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 注册送红包的活动
 */
@EventBus
public class RegisterEventListener implements ApplicationListener<RegisterEvent> {


    @Async
    @Override
    public void onApplicationEvent(RegisterEvent registerEvent) {

//        HwUser user = (HwUser) registerEvent.getSource();
//        if (user != null) {
//            try {
//                HwUserLog log = new HwUserLog();
//                log.setUserId(user.getId());
//                log.setLogType(UserLogServiceImpl.TYPE_LOG);
//                log.setLogContent("注册成功");
//                userLogService.insert(log);
//            } catch (IllegalServiceException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
