package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * 替换字符串
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 17:08
 */
public class Replace extends Function {

    Field field;

    Object oldStr;

    Object newStr;

    public Replace(Field field, Object oldStr, Object newStr) {
        this.field = field;
        this.oldStr = oldStr;
        this.newStr = newStr;
    }

    public Field getField() {
        return field;
    }

    public Replace setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getOldStr() {
        return oldStr;
    }

    public Replace setOldStr(Object oldStr) {
        this.oldStr = oldStr;
        return this;
    }

    public Object getNewStr() {
        return newStr;
    }

    public Replace setNewStr(Object newStr) {
        this.newStr = newStr;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || oldStr == null || newStr == null) {
            throw new SelfCheckException("field and oldStr and newStr can not be null in function Replace");
        }
    }
}
