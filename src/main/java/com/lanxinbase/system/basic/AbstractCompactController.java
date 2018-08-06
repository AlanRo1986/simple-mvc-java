package com.lanxinbase.system.basic;


import com.lanxinbase.constant.Constant;
import com.lanxinbase.model.common.UploadModel;
import com.lanxinbase.service.resource.IUploadService;
import com.lanxinbase.system.bus.event.ApplicationUserEvent;
import com.lanxinbase.system.callback.IFilePutCallback;
import com.lanxinbase.system.core.Application;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.utils.CommonUtils;
import com.lanxinbase.system.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alan.luo on 2017/9/18.
 */
@RestController
public abstract class AbstractCompactController extends AbstractCompact {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public AbstractCompactController(){
        super(AbstractCompactController.class);
    }

    public AbstractCompactController(Class classz) {
        super(classz);
    }


    /**
     * 本地化语言
     *
     * @param name
     * @param isInfo
     * @return
     */
    public String getLang(String name, boolean isInfo) {
        return Application.getInstance(null).getLang(name, isInfo ? Constant.langTypeInfo : Constant.langTypeErrors);
    }

    /**
     * 返回一个token
     *
     * @return
     */
    public String getToken() {
        String token = request.getHeader(Constant.tokenKey);
        if (StringUtils.isEmpty(token) || token.length() < 30) {
            return null;
        }
        return token;
    }

    /**
     * 返回客户端的IP地址
     *
     * @return
     */
    public String getClientIp() {
        return CommonUtils.getClientIp(request);
    }

    /**
     * 上传图片处理
     *
     * @param request
     * @return
     */
    public List<String> upload(HttpServletRequest request) {
        List<String> imgs = new ArrayList<>();
        try {

            MultipartHttpServletRequest nativeRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            if (nativeRequest != null) {
                Iterator<String> iterable = nativeRequest.getFileNames();
                int i = 0;
                if (iterable.hasNext()) {
                    while (iterable.hasNext()) {
                        String name = iterable.next();
                        i++;
                        uploadService.doSave(nativeRequest.getFile(name), i, new IFilePutCallback() {
                            @Override
                            public void success(int code, String info, UploadModel model) {
                                imgs.add(model.getUrl());
                            }

                            @Override
                            public void error(int code, String info) {

                            }
                        });
                    }
                }
            }
        } catch (IllegalServiceException e) {
            e.printStackTrace();
        }
        return imgs;
    }

    public void publisher(ApplicationUserEvent event) {
        eventPublisher.publishEvent(event);
    }

    public String formatDateTime(Integer time) {
        if (time != null && time > 86400) {
            return DateUtils.getFullDateTime(Long.parseLong(time + "000"));
        }
        return "";
    }

}
