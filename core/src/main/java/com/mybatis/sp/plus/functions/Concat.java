package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.arrays;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 21:40
 */
public class Concat  extends Function {

    List<Field> objs=new ArrayList<>();

    public Concat() {
    }

    public Concat(List<Field> objs) {
        this.objs = objs;
    }

    public Concat(Field... objs) {
        this.objs = arrays(objs);
    }

    public List<Field> getObjs() {
        return objs;
    }

    public void setObjs(List<Field> objs) {
        this.objs = objs;
    }

    public void addObjs(Field... objs) {
        if (this.objs==null) {
            this.objs = arrays(objs);
        }else {
            this.objs.addAll(Arrays.asList(objs));
        }
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (objs.size()==0){
            throw new SelfCheckException("objs can not be null in function Count");
        }
    }
}
