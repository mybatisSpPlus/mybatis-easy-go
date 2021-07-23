package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.actions.InsertInto;
import com.github.mybatis.easy.go.actions.Window;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.supportAnnotation.DatabaseVersion;
import com.github.mybatis.easy.go.windowFunctions.Over;
import com.github.mybatis.easy.go.windowFunctions.OverWindow;

import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 9:10
 */
public class MysqlLikeStepGenerator extends StepGenerator {

    public MysqlLikeStepGenerator(List<Action> actions) {
        super(actions, "`");
    }

    public void InsertIntoToStep(InsertInto insertInto) throws Exception {
        if (insertInto.isIgnore()) {
            steps.add(new Step("INSERT IGNORE INTO"));
        } else {
            steps.add(new Step("INSERT INTO"));
        }
        tableToStep(insertInto.getTable());
        if (insertInto.getFields().size() > 0) {
            steps.add(new Step("("));
            for (Field field : insertInto.getFields()) {
                fieldToStep(field);
                steps.add(new Step(","));
            }
            steps.removeLast();
            steps.add(new Step(")"));
        }
        if (insertInto.getValues().size() > 0) {
            steps.add(new Step("VALUES"));
            for (List<Object> values : insertInto.getValues()) {
                steps.add(new Step("("));
                for (Object value : values) {
                    if (value instanceof Function) {
                        functionToStep((Function) value);
                    } else {
                        steps.add(new Step().setStepValue(value));
                    }
                    steps.add(new Step(","));
                }
                steps.removeLast();
                steps.add(new Step(")"));
                steps.add(new Step(","));
            }
            steps.removeLast();
        }
    }

}
