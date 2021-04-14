package com.mybatis.sp.plus.meta;

import com.mybatis.sp.plus.conditions.*;
import com.mybatis.sp.plus.exception.SelfCheckException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:44
 */
public class Field {

    private String tableName;

    private String name;

    private Alias alias;

    public Field() {
    }

    public Field(String name) {
        this.name = name;
    }

    public Field(String name, Alias alias) {
        this.name = name;
        this.alias = alias;
    }

    public Field(String tableName, String name) {
        this.tableName = tableName;
        this.name = name;
    }

    public Field(String tableName, String name, Alias alias) {
        this.tableName = tableName;
        this.name = name;
        this.alias = alias;
    }

    public String getTableName() {
        return tableName;
    }

    public Field setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public Alias getAlias() {
        return alias;
    }

    public Field setAlias(Alias alias) {
        this.alias = alias;
        return this;
    }

    public Field as(Alias alias) {
        return setAlias(alias);
    }

    public Field as(String alias) {
        return setAlias(new Alias(alias));
    }


    public Between between(Object startValue, Object endValue) {
        return new Between(this,startValue,endValue);
    }
    public Eq eq(Object value){
        return new Eq(this,value);
    }

    public Neq neq( Object value){
        return new Neq(this,value);
    }

    public Gt gt(Object value){
        return new Gt(this,value);
    }

    public Gte gte(Object value){
        return new Gte(this,value);
    }

    public Lt lt( Object value){
        return new Lt(this,value);
    }

    public Lte lte( Object value){
        return new Lte(this,value);
    }

    public Like like( String value){
        return new Like(this,value);
    }

    public Regx regx( String value){
        return new Regx(this,value);
    }

    public In in( Object... values){
        return new In(this,values);
    }

    public IsNull isNull(){
        return new IsNull(this);
    }

    public IsNotNull isNotNull(){
        return new IsNotNull(this);
    }

    public void selfCheck() throws SelfCheckException {
        if (StringUtils.isBlank(name)){
            throw new SelfCheckException("field name can not be blank");
        }
    }
}
