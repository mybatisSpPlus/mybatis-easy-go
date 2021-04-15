package com.mybatis.sp.plus.step;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.actions.Limit;
import com.mybatis.sp.plus.functions.Concat;
import com.mybatis.sp.plus.functions.Instr;
import com.mybatis.sp.plus.meta.Alias;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/15 9:10
 */
public class OracleStepGenerator extends OracleLikeStepGenerator{

    public OracleStepGenerator(List<Action> actions) {
        super(actions);
    }

}
