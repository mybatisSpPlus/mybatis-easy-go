package com.github.mybatis.easy.go.windowFunctions.frame;

/**
 * 往后rows行
 */
public class Following extends Frame{
    int rows;

    public Following(int rows) {
        this.rows = rows;
    }

    public long getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
