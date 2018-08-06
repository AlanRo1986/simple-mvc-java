package com.lanxinbase.system.provider;

import com.lanxinbase.constant.Constant;
import com.lanxinbase.constant.ConstantAuthority;

import com.lanxinbase.model.AdminToken;
import com.lanxinbase.system.annotation.Provider;
import com.lanxinbase.system.basic.CompactProvider;
import com.lanxinbase.system.basic.ExceptionError;
import com.lanxinbase.system.exception.IllegalAccessDeniedException;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.listener.RequestListener;
import com.lanxinbase.system.provider.basic.ISecurity;
import com.lanxinbase.system.utils.DateUtils;
import com.lanxinbase.system.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


/**
 * Created by alan.luo on 2017/11/1.
 */
@Provider
public class SecurityProvider extends CompactProvider implements ISecurity {

//    @Autowired
//    private IUserTokenService tokenService;
//
//    @Autowired
//    private IAdminTokenService adminTokenService;
//
//    @Autowired
//    private IRoleAccessService roleAccessService;

    public SecurityProvider() {
        super(SecurityProvider.class);
    }

    @Override
    public boolean intersection() throws IllegalAccessDeniedException {

        RequestListener listener = RequestListener.getInstance();

        /**
         * check the http request version is or not valid.
         */
//        if (this.checkVersion(listener.getHeader(Constant.version)) == false){
//            return false;
//        }

        /**
         * if the ip address has in the black box,then return false.
         */
        if (checkIps(listener.getClientIp()) == false) {
            return false;
        }

        /**
         * The controller like this:
         * /api/test | /admin/test | /store/test
         */
        if (listener.getController().indexOf("/api/") == 0) {
            return checkLoginForUser(listener);
        } else if (listener.getController().indexOf("/admin/") == 0) {
            return checkRolesAccessForAdmin(listener);
        }

        /**
         * otherwise is invalid.
         */
        return false;
    }

    /**
     * 验证后台用户权限
     *
     * @param listener
     * @return
     * @throws IllegalAccessDeniedException
     */
    @Override
    public boolean checkRolesAccessForAdmin(RequestListener listener) throws IllegalAccessDeniedException {

        if (listener.getController().indexOf("login") > -1) {
            return true;
        }

        String token = listener.get("token");
        if (StringUtils.isEmpty(token) || token.length() != 32) {
            throw new IllegalAccessDeniedException(ExceptionError.error_unauthorized_invalid);
        }

        AdminToken adminToken = null;
//        try {
//            adminToken = adminTokenService.getRowByToken(token);
//        } catch (IllegalServiceException e) {
//            e.printStackTrace();
//        }

        /**
         * token过期所以找不到
         */
        if (adminToken == null) {
            throw new IllegalAccessDeniedException(ExceptionError.error_unauthorized_invalid);
        }

        /**
         * 在其他设备上登录过
         */
        if (!adminToken.getToken().toLowerCase().equals(token.toLowerCase())) {
            throw new IllegalAccessDeniedException(ExceptionError.error_unauthorized_access_2);
        }

        /**
         * 超级管理员不需要鉴权
         */
        if (adminToken.getAdmId() != 1) {
//            try {
//                if (!roleAccessService.checkAdminRoleAccess(listener.getController(), listener.getAction(), adminToken.getAdmin().getRoleId())) {
//                    throw new IllegalAccessDeniedException(ExceptionError.error_access_denied);
//                }
//            } catch (IllegalServiceException e) {
//                e.printStackTrace();
//                throw new IllegalAccessDeniedException(e.getMessage());
//            }
        }


        //Integer id = (Integer) requestListener.getSession().getAttribute("adminId");
        //记得保存对象到session。adminId,admin日志需要使用
        listener.getSession().setAttribute("adminId", adminToken.getAdmId());
        listener.getSession().setAttribute(AdminToken.class.getName(), adminToken);
        return true;
    }

    /**
     * 验证用户是否登录
     *
     * @param listener
     * @return
     * @throws IllegalAccessDeniedException
     */
    @Override
    public boolean checkLoginForUser(RequestListener listener) throws IllegalAccessDeniedException {

        String ctl = listener.getController();
        /**
         * /api/getUserBikeRecord
         */
//        if (!listener.getAction().equals("GET")) {
//            ctl += "/" + listener.getAction().toLowerCase();
//        }

//        if (ConstantAuthority.authority.containsKey(ctl)) {
//            if (ConstantAuthority.authority.get(ctl)) {
//                String token = listener.getHeader(Constant.tokenKey);
//                if (token == null || token.length() != 32) {
//                    throw new IllegalAccessDeniedException(ExceptionError.error_need_logined);
//                }
//
//                try {
//                    /**
//                     * 有token,但是数据没有,可能是在其他地方登录过!
//                     */
//                    UserToken userToken = tokenService.getByToken(token);
//                    if (userToken == null) {
//                        throw new IllegalAccessDeniedException(ExceptionError.error_unauthorized_access_2);
//                    }
//
//                    /**
//                     * 无效,或者过期
//                     */
//                    if (userToken.getStatus() == 0 || userToken.getExpireIn() < (DateUtils.getTime() / 1000)) {
//                        throw new IllegalAccessDeniedException(ExceptionError.error_user_token_invalid);
//                    }
//
//                    //Integer id = (Integer) requestListener.getSession().getAttribute("adminId");
//                    //记得保存对象到session。因为经常使用
//                    listener.getSession().setAttribute("userId", userToken.getUserId());
//                    listener.getSession().setAttribute(UserToken.class.getName(), userToken);
//
//                } catch (IllegalServiceException e) {
//                    e.printStackTrace();
//                    throw new IllegalAccessDeniedException(e.getCode());
//                }
//            }
//        }else{
//            String token = listener.getHeader(Constant.tokenKey);
//            if (token != null && token.length() == 32) {
//                try {
//                    UserToken userToken = tokenService.getByToken(token);
//                    if(userToken!=null){
//                        listener.getSession().setAttribute("userId", userToken.getUserId());
//                    }
//                } catch (IllegalServiceException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }

        return true;
    }


    /**
     * 后期加入的IP黑名单
     *
     * @param ip
     * @return
     * @throws IllegalAccessDeniedException
     */
    @Override
    public boolean checkIps(String ip) throws IllegalAccessDeniedException {
        return true;
    }

    @Override
    public boolean checkVersion(String version) throws IllegalAccessDeniedException {
        return true;
    }

    public String makeUserToken(String hash) {
        return Md5Utils.md5(hash + DateUtils.getTime());
    }

}
