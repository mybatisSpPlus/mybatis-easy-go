package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:29
 */
public class Convert extends Function {

    Field field;

    String targetCharset;

    String sourceCharset;

    public Convert() {
    }

    public Convert(Field field, String targetCharset) {
        this.field = field;
        this.targetCharset = targetCharset;
    }

    public Convert(Field field, String targetCharset, String sourceCharset) {
        this.field = field;
        this.targetCharset = targetCharset;
        this.sourceCharset = sourceCharset;
    }

    public Field getField() {
        return field;
    }

    public Convert setField(Field field) {
        this.field = field;
        return this;
    }

    public String getTargetCharset() {
        return targetCharset;
    }

    public void setTargetCharset(String targetCharset) {
        this.targetCharset = targetCharset;
    }

    public String getSourceCharset() {
        return sourceCharset;
    }

    public void setSourceCharset(String sourceCharset) {
        this.sourceCharset = sourceCharset;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || StringUtils.isBlank(targetCharset)) {
            throw new SelfCheckException("field、targetCharset can not be null in function Format");
        }
    }
}
