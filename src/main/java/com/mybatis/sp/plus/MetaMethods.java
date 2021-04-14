package com.mybatis.sp.plus;

import com.mybatis.sp.plus.meta.Alias;
import com.mybatis.sp.plus.meta.ConstantField;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Table;

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
        return new Table(name);
    }

    public static Table table(String name, Alias alias){
        return new Table(name,alias);
    }

    public static Field field(String name){
        return new Field(name);
    }

    public static Field field(String name, Alias alias){
        return new Field(name,alias);
    }

    public static Field field(String tableName, String name){
        return new Field(tableName,name);
    }

    public static Field field(String tableName, String name, Alias alias){
        return new Field(tableName,name,alias);
    }

    public static ConstantField constantField(Object constant){
        return new ConstantField(constant);
    }

    public static Alias alias(String name){
        return new Alias(name);
    }

}
