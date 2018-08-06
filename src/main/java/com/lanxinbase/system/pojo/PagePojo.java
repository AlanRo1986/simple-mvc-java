package com.lanxinbase.system.pojo;



import com.lanxinbase.constant.Constant;
import com.lanxinbase.model.basic.Model;

import java.util.List;

/**
 * Created by alan.luo on 2017/10/26.
 */
public class PagePojo<T> extends Model {

    private Long page;
    private Long pageSize;
    private Long pageTotal;
    private Long itemNum;
    private List<T> list;

    public PagePojo(){
        this.pageSize = Long.valueOf(Constant.PAGE_SIZE);
        this.page = 0L;
    }

    /**
     *
     * @param page 当前页数
     */
    public PagePojo(Long page){
        this.pageSize = Long.valueOf(Constant.PAGE_SIZE);
        this.page = page;
    }

    /**
     *
     * @param page 当前页数
     * @param pageSize 项目总数
     */
    public PagePojo(Long page, Long pageSize){
        this.pageSize = pageSize;
        this.page = page;
    }

    /**
     *
     * @param page 当前页数
     * @param pageSize 项目总数
     * @param itemNum 每页行数
     */
    public PagePojo(Long page, Long pageSize, Long itemNum){
        this.pageSize = pageSize;
        this.page = page;
        this.setItemNum(itemNum);
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Long pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Long getItemNum() {
        return itemNum;
    }

    public void setItemNum(Long itemNum) {
        if(itemNum == null){
            itemNum = 0L;
        }
        this.itemNum = itemNum;
        setPageTotal((long) Math.ceil((double)itemNum / (double)getPageSize()));
        setPage((page/pageSize)+1);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
