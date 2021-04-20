package com.mybatis.sp.plus.meta;

import com.mybatis.sp.plus.Meta;
import com.mybatis.sp.plus.conditions.*;
import com.mybatis.sp.plus.exception.SelfCheckException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:44
 */
public class Field extends Meta {
    /**
     * 比如mysql的BINARY
     */
    private String specialPrefix;

    private String tableName;

    private String name;

    private Alias alias;

    public Field() {
    }

    public Field(String name) {
        this.name = name.trim();
    }

    public Field(String name, Alias alias) {
        this.name = name.trim();
        this.alias = alias;
    }

    public Field(String tableName, String name) {
        this.tableName = tableName.trim();
        this.name = name;
    }


    public Field(String tableName, String name, Alias alias) {
        this.tableName = tableName.trim();
        this.name = name.trim();
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
        this.name = name.trim();
        return this;
    }

    public String getSpecialPrefix() {
        return specialPrefix;
    }

    public Field setSpecialPrefix(String specialPrefix) {
        this.specialPrefix = specialPrefix;
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
        return new Lt(this, value);
    }

    public Lte lte(Object value) {
        return new Lte(this, value);
    }

    public Like like(String value) {
        return new Like(this, value);
    }

    public StartWith startWith(String value) {
        return new StartWith(this, value);
    }

    public EndWith endWith(String value) {
        return new EndWith(this, value);
    }

    public Regx regx(String value) {
        return new Regx(this, value);
    }

    public In in(Object... values) {
        return new In(this, values);
    }

    public In in(Table table) {
        return new In(this, table);
    }

    public IsNull isNull() {
        return new IsNull(this);
    }

    public IsNotNull isNotNull() {
        return new IsNotNull(this);
    }

    public void selfCheck() throws SelfCheckException {
        if (StringUtils.isBlank(name) && alias == null) {
            throw new SelfCheckException("field name ,alias can not be both blank");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;
        Field field = (Field) o;
        return Objects.equals(getTableName(), field.getTableName()) &&
                getName().equals(field.getName()) &&
                Objects.equals(getAlias(), field.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTableName(), getName(), getAlias());
    }
}
