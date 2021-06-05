package com.github.mybatis.sp.plus.conditions;

import com.github.mybatis.sp.plus.Condition;
import com.github.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:39
 */
public class And extends Condition {

    List<Condition> andCondition=new ArrayList<>();

    public And() {
    }

    public And(List<Condition> andCondition) {
        this.andCondition = andCondition;
    }

    public And(Condition... andCondition) {
        this.andCondition.addAll(Arrays.asList(andCondition));
    }

    public List<Condition> getAndCondition() {
        return andCondition;
    }

    public And setAndCondition(List<Condition> andCondition) {
        this.andCondition = andCondition;
        return this;
    }

    public And addAnd(Condition... conditions){
        andCondition.addAll(Arrays.asList(conditions));
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        //此处不再进行check，而是将EmptyCondition去掉，如果最终and中没有条件，在构建step时直接跳过
        Iterator<Condition> iterator = andCondition.iterator();
        while (iterator.hasNext()) {
            Condition condition = iterator.next();
            if (condition instanceof EmptyCondition) {
                iterator.remove();
            }
        }
    }
}
