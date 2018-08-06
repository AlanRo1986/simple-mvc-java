package com.lanxinbase.system.provider.basic;

import com.lanxinbase.system.exception.IllegalAccessDeniedException;
import com.lanxinbase.system.listener.RequestListener;

/**
 * Created by alan.luo on 2017/11/1.
 */
public interface ISecurity {

    boolean intersection() throws IllegalAccessDeniedException;

    boolean checkRolesAccessForAdmin(RequestListener listener) throws IllegalAccessDeniedException;

    boolean checkLoginForUser(RequestListener listener) throws IllegalAccessDeniedException;

    boolean checkIps(String ip) throws IllegalAccessDeniedException;

    boolean checkVersion(String version) throws IllegalAccessDeniedException;

}
