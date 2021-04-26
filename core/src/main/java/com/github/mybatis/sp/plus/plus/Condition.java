package com.github.mybatis.sp.plus.plus;

import com.github.mybatis.sp.plus.plus.conditions.And;
import com.github.mybatis.sp.plus.plus.conditions.Or;
import com.github.mybatis.sp.plus.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:40
 */
public abstract class Condition extends Field {

    public And and(Condition condition) {
        if (this instanceof And) {
            ((And) this).addAnd(condition);
            return (And) this;
        } else {
            And and = new And();
            and.addAnd(condition);
            and.addAnd(this);
            return and;
        }
    }
    public Or or(Condition condition){
        if (this instanceof Or){
            ((Or) this).addOr(condition);
            return (Or) this;
        }else {
            Or or = new Or();
            or.addOr(condition);
            or.addOr(this);
            return or;
        }
    }

}
