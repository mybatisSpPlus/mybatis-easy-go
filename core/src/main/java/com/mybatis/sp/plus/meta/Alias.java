package com.mybatis.sp.plus.meta;

import com.mybatis.sp.plus.Meta;
import com.mybatis.sp.plus.exception.SelfCheckException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:44
 */
public class Alias  extends Meta {

    private String name;

    public Alias(String name) {
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    public Alias setName(String name) {
        this.name = name.trim();
        return this;
    }

    public void selfCheck() throws SelfCheckException {
        if (StringUtils.isBlank(name)){
            throw new SelfCheckException("alias name can not be blank");
        }
    }
}
