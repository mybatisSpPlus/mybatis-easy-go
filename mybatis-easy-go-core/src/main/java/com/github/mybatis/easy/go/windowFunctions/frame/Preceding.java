package com.github.mybatis.easy.go.windowFunctions.frame;

/**
 * 往前row行
 */
public class Preceding extends Frame{
    int rows;

    public Preceding(int rows) {
        this.rows = rows;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
