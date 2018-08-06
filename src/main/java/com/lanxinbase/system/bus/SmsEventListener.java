package com.lanxinbase.system.bus;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.lanxinbase.system.annotation.EventBus;
import com.lanxinbase.system.bus.event.SmsEvent;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.model.SmsModel;
import com.lanxinbase.system.provider.SmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by alan on 2018/6/25.
 * 监听短信发送的事件
 */
@EventBus
public class SmsEventListener implements ApplicationListener<SmsEvent> {


    @Autowired
    private SmsProvider smsProvider;

    @Async
    @Override
    public void onApplicationEvent(SmsEvent smsEvent) {
        SmsModel sms = (SmsModel) smsEvent.getSource();
        if (sms != null) {
            try {
                SendSmsResponse response = smsProvider.send(sms.getMobile(), sms.getTemplateCode(), sms.getParam());

//                HwMobileMsg msg = new HwMobileMsg();
//                msg.setMobile(sms.getMobile());
//                msg.setContent(CommonUtils.parserMoji(sms.getTemplateCode() + ":" + sms.getParam(),false));
//                msg.setResultCode(response.getCode());
//                mobileMsgService.insert(msg);
            } catch (IllegalServiceException e) {
                e.printStackTrace();
            }

        }
    }
}
