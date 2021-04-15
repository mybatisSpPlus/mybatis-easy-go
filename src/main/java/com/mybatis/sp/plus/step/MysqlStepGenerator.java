package com.mybatis.sp.plus.step;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.Meta;
import com.mybatis.sp.plus.actions.*;
import com.mybatis.sp.plus.conditions.*;
import com.mybatis.sp.plus.functions.*;
import com.mybatis.sp.plus.meta.*;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/15 9:10
 */
public class MysqlStepGenerator extends StepGenerator {

    public MysqlStepGenerator(List<Action> actions) {
        super(actions,"`");
    }

}
