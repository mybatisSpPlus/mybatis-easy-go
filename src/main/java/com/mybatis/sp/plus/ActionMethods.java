package com.mybatis.sp.plus;

import com.mybatis.sp.plus.actions.*;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 22:44
 */
public class ActionMethods {
    /*
    action 的快捷生成方法
     */
    public static Select select() {
        return new QueryBuilders().select();
    }
    public static Select select(Field... fields) {
        return new QueryBuilders().select(fields);
    }
    public static Select select(String... fieldNames) {
        List<Field> fields=new ArrayList<>();
        for (String fieldName : fieldNames) {
            fields.add(QueryBuilderHelper.fieldNameToField(fieldName));
        }
        return new QueryBuilders().select(fields);
    }

    public static Update update() {
        return new QueryBuilders().update();
    }

    public static Insert insert() {
        return new QueryBuilders().insert();
    }

    public static Delete delete() {
        return new QueryBuilders().delete();
    }

    public static Order Order(){
        return new Order();
    }
    public static Order Order(String fieldName){
        return new Order(QueryBuilderHelper.fieldNameToField(fieldName),false);
    }
    public static Order Order(String fieldName, boolean desc){
        return new Order(QueryBuilderHelper.fieldNameToField(fieldName),desc);
    }
    public static Order Order(Field field){
        return new Order(field,false);
    }
    public static Order Order(Field field, boolean desc){
        return new Order(field,desc);
    }

    public static Limit limit(){
        return new Limit();
    }

    public static Limit limit(int limit){
        return new Limit(limit);
    }

    public static Limit limit(int limit, int offset){
        return new Limit(limit,offset);
    }

}
