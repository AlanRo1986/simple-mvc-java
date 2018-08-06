package com.lanxinbase.system.basic;

import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.listener.RequestListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by alan.luo on 2017/9/18.
 */
@RequestMapping(value = "/admin")
@RestController
public abstract class BasicAdminController extends AbstractCompactController {

//    @Autowired
//    private IAdminLogService adminLogService;

    public BasicAdminController(){
        super(BasicAdminController.class);
    }

    public BasicAdminController(Class classz) {
        super(classz);
    }


    /**
     * 保存后台日志
     * @param info
     */
    public void saveOperationLog(String info) throws IllegalServiceException{
        RequestListener requestListener = RequestListener.getInstance();
        Integer id = (Integer) requestListener.getSession().getAttribute("adminId");

//        HwAdminLog log = new HwAdminLog();
//        log.setAdminId(id);
//        log.setCtl(requestListener.getController());
//        log.setAct(requestListener.getAction());
//        log.setLogInfo(info);
//        log.setLogIp(getClientIp());
//        adminLogService.insert(log);
    }



}
