package com.lanxinbase.system.bus;


import com.lanxinbase.system.annotation.EventBus;
import com.lanxinbase.system.bus.event.LoginEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by alan.luo on 2017/10/17.
 */
@EventBus
public class LoginEventListener implements ApplicationListener<LoginEvent> {

//    @Autowired
//    private IUserLogService userLogService;

    @Async
    @Override
    public void onApplicationEvent(LoginEvent event) {
        Integer userId = (Integer) event.getSource();
        if(userId != null && userId >0){
//            HwUserLog log = new HwUserLog();
//            log.setUserId(user.getId());
//            log.setLogType(UserLogServiceImpl.TYPE_LOG);
//            log.setLogContent("登陆成功");
//            try {
//                userLogService.insert(log);
//            } catch (IllegalServiceException e) {
//                e.printStackTrace();
//            }
        }
    }
}
