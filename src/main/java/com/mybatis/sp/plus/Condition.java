package com.mybatis.sp.plus;

import com.mybatis.sp.plus.conditions.And;
import com.mybatis.sp.plus.conditions.Or;
import com.mybatis.sp.plus.exception.SelfCheckException;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:40
 */
public abstract class Condition {

    public And and(Condition condition){
        if (this instanceof And){
            ((And) this).addAnd(condition);
            return (And) this;
        }else {
            And and=new And();
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

    public abstract void selfCheck() throws SelfCheckException;

}
