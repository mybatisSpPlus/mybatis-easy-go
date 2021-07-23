package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 将分区中的有序数据分为n个等级，记录等级数
 */
@_Over
public class Ntile extends Function {

    int groupCount;

    public Ntile(int groupCount) {
        this.groupCount = groupCount;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
