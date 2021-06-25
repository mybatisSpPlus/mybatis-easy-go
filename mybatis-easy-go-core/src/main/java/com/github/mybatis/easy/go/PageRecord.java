package com.github.mybatis.easy.go;

import java.util.ArrayList;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/5/20 20:22
 */
public class PageRecord<E> extends ArrayList<E> {
    /**
     * 页码，从1开始
     */
    private int pageIndex;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pageCount;

    public PageRecord() {
        super();
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public PageRecord<E> setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageRecord<E> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public PageRecord<E> setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getPageCount() {
        return pageCount;
    }

    public PageRecord<E> setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}
