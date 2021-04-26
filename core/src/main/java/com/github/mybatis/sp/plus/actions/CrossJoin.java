package com.github.mybatis.sp.plus.actions;

import com.github.mybatis.sp.plus.meta.Table;
import com.mybatis.sp.plus.annotation.*;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:43
 */
@_Set
@_Where
@_Union
@_UnionAll
@_CrossJoin
@_LeftJoin
@_RightJoin
@_InnerJoin
@_FullJoin
@_GroupBy
@_OrderBy
@_Limit
public class CrossJoin extends Join {

    public CrossJoin(Table table) {
        super(table);
    }

}
