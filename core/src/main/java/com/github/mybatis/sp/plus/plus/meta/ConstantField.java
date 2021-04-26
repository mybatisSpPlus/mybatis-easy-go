package com.github.mybatis.sp.plus.plus.meta;

import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 17:24
 */
public class ConstantField extends Field {

    Object constant;

    public ConstantField() {
    }

    public ConstantField(Object constant) {
        this.constant = constant;
    }

    public Object getConstant() {
        return constant;
    }

    public ConstantField setConstant(Object constant) {
        this.constant = constant;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (constant==null|| StringUtils.isBlank(constant.toString())){
            throw new SelfCheckException("ConstantField constant can not be blank or null");
        }
    }

}
