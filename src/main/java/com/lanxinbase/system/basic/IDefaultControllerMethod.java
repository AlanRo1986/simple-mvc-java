package com.lanxinbase.system.basic;

import com.lanxinbase.model.basic.Model;
import com.lanxinbase.system.core.ResultResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by alan.luo on 2017/9/18.
 */
public interface IDefaultControllerMethod<T extends Model> {

    ResultResp<Map<String, Object>> _doGet(HttpServletRequest request, HttpServletResponse response);

    ResultResp<Map<String, Object>> _doPost(T obj, HttpServletRequest request, HttpServletResponse response);

    ResultResp<Map<String, Object>> _doPut(Integer id, T obj, HttpServletRequest request, HttpServletResponse response);

    ResultResp<Map<String, Object>> _doDelete(Integer id, HttpServletRequest request, HttpServletResponse response);
}
