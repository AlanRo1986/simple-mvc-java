package com.lanxinbase.system.basic;

import com.lanxinbase.model.basic.Model;
import com.lanxinbase.model.common.WhereModel;
import com.lanxinbase.system.exception.IllegalServiceException;
import com.lanxinbase.system.pojo.PagePojo;

/**
 * All the service and repository must be implements this interface.
 *
 * Created by alan.luo on 2017/9/24.
 */
public interface IComponentExtend<T extends Model> {

    /**
     * 插入一行数据
     * @param record
     * @return
     * @throws IllegalServiceException
     */
    int insert(T record) throws IllegalServiceException;

    /**
     * 根据ID获取一行数据
     * @param id
     * @return
     * @throws IllegalServiceException
     */
    T getRowById(Integer id) throws IllegalServiceException;

    /**
     * 根据ID更新数据
     * @param record
     * @return
     * @throws IllegalServiceException
     */
    int updateById(T record) throws IllegalServiceException;

    /**
     * 根据ID删除一行数据
     * @param id
     * @return
     * @throws IllegalServiceException
     */
    int deleteById(Integer id) throws IllegalServiceException;

    /**
     * 根据条件模型类获取所有的数据，需要使用pagePojo封装成分页对象
     *
     * @param model 条件对象模型
     * @return
     * @throws IllegalServiceException
     */
    PagePojo<T> getAll(WhereModel model) throws IllegalServiceException;

    /**
     * 根据条件模型类获取数据总数
     * @param model
     * @return
     * @throws IllegalServiceException
     */
    long countAll(WhereModel model) throws IllegalServiceException;

    /**
     * 解析对象，通常需要格式化数据中的对象，比如时间、金钱
     * @param obj
     * @return
     * @throws IllegalServiceException
     */
    T parser(T obj) throws IllegalServiceException;

}
