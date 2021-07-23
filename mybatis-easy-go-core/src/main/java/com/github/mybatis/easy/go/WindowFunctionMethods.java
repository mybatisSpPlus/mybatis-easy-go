package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.windowFunctions.*;

/**
 * 窗口函数的快捷生成方式
 */
public class WindowFunctionMethods {

    /**
     * 数据项在分组中的行号
     * @return
     */
    public static RowNumber rowNumber(){
        return new RowNumber();
    }

    /**
     * 数据项在分组中的排名，排名相等会在名次中留下空位
     * @return
     */
    public static Rank rank(){
        return new Rank();
    }

    /**
     * 分组内当前行的RANK值-1/分组内总行数-1
     * @return
     */
    public static PercentRank percentRank(){
        return new PercentRank();
    }

    /**
     * 数据项在分组中的排名，排名相等会在名次中不会留下空位
     * @return
     */
    public static DenseRank denseRank(){
        return new DenseRank();
    }

    /**
     * 小于等于当前值的行数/分组内总行数
     * @return
     */
    public static CumeDist cumeDist(){
        return new CumeDist();
    }

    /**
     * 取分组内排序后，截止到当前行，第一个值
     * @param field
     * @return
     */
    public static FirstValue firstValue( Field field){
        return new FirstValue(field);
    }

    /**
     * 取分组内排序后，截止到当前行，最后一个值
     * @param field
     * @return
     */
    public static LastValue lastValue( Field field){
        return new LastValue(field);
    }

    /**
     * 用于统计窗口内往上第n行值
     * @param field
     * @param rowCount
     * @return
     */
    public static Lag lag(Field field,int rowCount){
        return new Lag(field,rowCount);
    }

    /**
     * 用于统计窗口内往下第n行值
     * @param field
     * @param rowCount
     * @return
     */
    public static Lead lead(Field field,int rowCount){
        return new Lead(field,rowCount);
    }

    /**
     * 返回窗口中第n个expr的值。expr可以是表达式，也可以是列名
     * @param field
     * @param index
     * @return
     */
    public static NthValue nthValue(Field field,int index){
        return new NthValue(field,index);
    }

    /**
     * 将分区中的有序数据分为n个等级，记录等级数
     * @param groupCount
     * @return
     */
    public static Ntile ntile(int groupCount){
        return new Ntile(groupCount);
    }
}
