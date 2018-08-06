package com.lanxinbase.system.basic;

import com.lanxinbase.model.common.WhereModel;
import com.lanxinbase.system.exception.IllegalComponentException;

import java.util.List;

/**
 * All the service and repository must be implements this interface.
 *
 * Created by alan.luo on 2017/9/24.
 */
public interface IComponent<T> {

    T getOne(WhereModel model) throws IllegalComponentException;

    T getRow(WhereModel model) throws IllegalComponentException;

    List<T> getAll(WhereModel model) throws IllegalComponentException;

    Integer count(WhereModel model) throws IllegalComponentException;

    Integer insert(T model) throws IllegalComponentException;

    Integer update(T model) throws IllegalComponentException;

    Integer delete(WhereModel model) throws IllegalComponentException;

}
