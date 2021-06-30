package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 21:40
 */
public class Concat extends Function {

    List<Field> objs = new ArrayList<>();

    String separator;

    public Concat() {
    }

    public Concat(List<Field> objs) {
        this.objs = objs;
    }

    public Concat(Field... objs) {
        this.objs = QueryBuilderHelper.arrays(objs);
    }

    public Concat(String separator, Field... objs) {
        this.separator = separator;
        this.objs = QueryBuilderHelper.arrays(objs);
    }

    public List<Field> getObjs() {
        return objs;
    }

    public void setObjs(List<Field> objs) {
        this.objs = objs;
    }

    public void addObjs(Field... objs) {
        if (this.objs == null) {
            this.objs = QueryBuilderHelper.arrays(objs);
        } else {
            this.objs.addAll(Arrays.asList(objs));
        }
    }

    public String getSeparator() {
        return separator;
    }

    public Concat withSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (objs.size() == 0) {
            throw new SelfCheckException("objs can not be null in function Count");
        }
    }
}
