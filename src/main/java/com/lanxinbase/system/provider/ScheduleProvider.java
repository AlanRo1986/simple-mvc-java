package com.lanxinbase.system.provider;

import com.lanxinbase.system.basic.BasicProvider;
import com.lanxinbase.system.annotation.Provider;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by alan.luo on 2017/9/19.
 */
@Provider
public class ScheduleProvider extends BasicProvider {


    public ScheduleProvider() {

    }


    //@Async
    public void doAsync() {
        println("------doAsync-------");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("doAsync:"+System.currentTimeMillis());
        println("------end-------");

    }

    //@Scheduled(cron = "0 0 2 * * ?")//每天凌晨2点执行
    //@Scheduled(cron = "0/20 * * * * ?")//每10秒执行一次
    //@Scheduled(cron = "0 0/1 * * * ?")//每分钟执行一次
    @Scheduled(cron = "0 0 * * * ?")//每小时执行一次
    public void check() {

    }
}
