package com.github.mybatis.sp.plus.plus.functions;

import com.github.mybatis.sp.plus.plus.Function;
import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.plus.meta.Field;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:29
 */
public class Format  extends Function {

    Field field;

    String format;

    public Format() {
    }

    public Format(Field field, String format) {
        this.field = field;
        this.format = format;
    }

    public Field getField() {
        return field;
    }

    public Format setField(Field field) {
        this.field = field;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public Format setFormat(String format) {
        this.format = format;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null|| StringUtils.isBlank(format)){
            throw new SelfCheckException("field、format can not be null in function Format");
        }
    }
}
