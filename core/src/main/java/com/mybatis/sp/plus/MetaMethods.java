package com.mybatis.sp.plus;

import com.mybatis.sp.plus.meta.*;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;
import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 22:44
 */
public class MetaMethods {
    /**
     * meta快捷方法
     * @return
     */
    public static Table table(){
        return new Table();
    }

    public static Table table(String name){
        return tableNameToTable(name);
    }

    public static Table table(String name, Alias alias){
        return tableNameToTable(name).setAlias(alias);
    }

    public static Field field(String name){
        return fieldNameToField(name);
    }

    public static Field field(String name, Alias alias){
        return fieldNameToField(name).setAlias(alias);
    }

    public static Field field(String tableName, String name){
        return new Field(tableName,name);
    }

    public static Field field(String tableName, String name, Alias alias) {
        return new Field(tableName, name, alias);
    }

    public static ConstantField constantField(Object constant) {
        return new ConstantField(constant);
    }

    public static AllField allField() {
        return new AllField();
    }

    public static Alias alias(String name) {
        return new Alias(name);
    }

    public static Order order(Field field) {
        return new Order(field, false);
    }

    public static Order order(String fieldName){
        return new Order(fieldNameToField(fieldName),false);
    }

    public static Order order(Field field,boolean Desc){
        return new Order(field,Desc);
    }

    public static Order order(String fieldName,boolean Desc){
        return new Order(fieldNameToField(fieldName),Desc);
    }

}
