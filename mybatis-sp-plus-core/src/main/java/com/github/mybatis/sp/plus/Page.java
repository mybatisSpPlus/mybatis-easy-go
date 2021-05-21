package com.github.mybatis.sp.plus;

import java.util.ArrayList;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/5/20 20:22
 */
public class Page<E> extends ArrayList<E> {
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
    private int pagesCount;

    public Page() {
        super();
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public Page<E> setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Page<E> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public Page<E> setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public Page<E> setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
        return this;
    }
}
